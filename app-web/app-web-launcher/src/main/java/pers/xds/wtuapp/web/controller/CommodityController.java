package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import pers.xds.wtuapp.errorcode.ResponseCode;
import pers.xds.wtuapp.errorcode.ResponseWrapper;
import pers.xds.wtuapp.web.aop.RateLimit;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.group.UpdateGroup;
import pers.xds.wtuapp.web.es.bean.EsCommodity;
import pers.xds.wtuapp.web.redis.common.Duration;
import pers.xds.wtuapp.web.service.CommodityService;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;

import java.util.List;


/**
 * 商品接口
 * @author DeSen Xu
 * @date 2022-09-09 11:03
 */
@RestController
@PreAuthorize(SecurityConstant.EL_AUTHENTICATED)
@RequestMapping("commodity")
public class CommodityController {

    private CommodityService commodityService;

    @Autowired
    public void setCommodityService(CommodityService commodityService) {
        this.commodityService = commodityService;
    }


    /**
     * 每周最多上传的商品数量
     */
    public static final int MAX_WEEK_POST_COUNT = 21;

    /**
     * 创建一个商品。
     * <p>
     * 商品的预览图与上传文件下发的指定路径不同，{@link pers.xds.wtuapp.web.service.config.cos.SignInfo#path}一般为<code>image/todo/xx</code>
     * 该路径下一般存放未处理(未压缩和格式转换)的图片，在图片处理完毕后，图片将自动移动到用户空间下。
     * <p>
     * 例如用户1上传了文件到 <code>image/todo/file.png</code>，在服务器进行处理后，会将图片转换并存放到<code>image/1/file.webp</code>下
     * <p>
     * 例如用户2上传了文件到 <code>image/todo/file-xxx.png</code>，在服务器进行处理后，会将图片转换并存放到<code>image/2/file-xxx.webp</code>下
     * <p>
     * <b>因此commodity的图片格式一定要满足：<code>image/{用户id}/xxx.webp</code></b>
     * @param commodity 商品参数
     * @return 创建成功后的商品id
     */
    @PostMapping("create")
    @RateLimit(value = MAX_WEEK_POST_COUNT, duration = Duration.WEEK, limitMessage = "您每周最多可以发布" + MAX_WEEK_POST_COUNT + "个商品")
    public ResponseWrapper<Integer> createCommodity(@Validated Commodity commodity) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        commodity.setOwnerId(userPrincipal.getId());
        return ResponseWrapper.success(commodityService.insertCommodity(commodity));
    }

    /**
     * 查询商品信息
     * @param id 商品id
     * @return 商品详细信息，可能为空
     */
    @GetMapping("/op/{id}")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseWrapper<Commodity> queryCommodity(@PathVariable int id) {
        return ResponseWrapper.success(commodityService.queryCommodity(id));
    }

    /**
     * 一天最多锁定的次数
     */
    public static final int MAX_LOCK_ACTION_PER_DAY = 15;

    /**
     * 锁定某个商品，并创建交易记录，表示买家想要购买这个商品。
     * @param id 商品id
     * @return 是否成功
     */
    @PostMapping("/op/{id}/lock")
    @RateLimit(value = MAX_LOCK_ACTION_PER_DAY, limitMessage = "您一天最多最多只能锁定" + MAX_LOCK_ACTION_PER_DAY + "次")
    public ResponseWrapper<Integer> lockCommodity(@PathVariable int id,
                                                  @RequestParam(value = "c", required = false, defaultValue = "1") int count,
                                                  @RequestParam(value = "r", required = false) String remark) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseWrapper.success(commodityService.lockCommodity(id, userPrincipal.getId(), count, remark));
    }

    /**
     * 最大搜索长度
     */
    public static final int MAX_SEARCH_SIZE = 30;

    /**
     * 根据名称搜索商品
     * @param search 搜索名称
     * @param page 第几页
     * @return 搜索内容
     */
    @GetMapping("search")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseWrapper<List<EsCommodity>> searchCommodityByName(@RequestParam("s") String search,
                                                                    @RequestParam(value = "z", required = false, defaultValue = "10") int size,
                                                                    @RequestParam(value = "p", required = false, defaultValue = "0") int page) {
        if (search.length() > MAX_SEARCH_SIZE) {
            return ResponseWrapper.fail(ResponseCode.REQUEST_PARAMETERS_ERROR, "搜索字符串太长");
        }
        return ResponseWrapper.success(commodityService.searchCommodityByName(search, page, size));
    }

    /**
     * 获取用户正在售卖的商品数量
     */
    @GetMapping("selling_count")
    public ResponseWrapper<Integer> getSellingCount() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseWrapper.success(commodityService.querySellingCount(userPrincipal.getId()));
    }

    /**
     * 获取用户上传的商品
     */
    @GetMapping("uploaded")
    public ResponseWrapper<List<Commodity>> getUploadedCommodity(
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseWrapper.success(commodityService.queryUserCommodity(userPrincipal.getId(), page, size));
    }

    /**
     * 更新商品
     */
    @PostMapping("/op/{cid}/update")
    public ResponseWrapper<Void> updateCommodity(@Validated(UpdateGroup.class) Commodity commodity, @PathVariable int cid) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        commodityService.updateCommodity(userPrincipal.getId(), cid, commodity);
        return ResponseWrapper.success();
    }

    /**
     * 下架商品
     */
    @PostMapping("/op/{cid}/close")
    public ResponseWrapper<Void> takeDownCommodity(@PathVariable int cid) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        commodityService.takeDownCommodity(userPrincipal.getId(), cid);
        // not exist
        return ResponseWrapper.success();
    }

    /**
     * 获取每日推荐
     * @param maxId 当前允许接收的最大id<p>
     *              例如将该值设为10，则返回的商品中最大id一定为10。
     *              为空时默认从最大开始选
     */
    @GetMapping("suggest")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseWrapper<List<Commodity>> getRecommend(
            @RequestParam(required = false, value = "m") Integer maxId,
            @RequestParam(required = false, value = "s", defaultValue = "8") int size
    ) {
        final int maxSize = 8;
        if (size > maxSize) {
            return ResponseWrapper.fail(ResponseCode.REQUEST_PARAMETERS_ERROR);
        }
        return ResponseWrapper.success(commodityService.getRecommend(maxId, size));
    }

}

package pers.xds.wtuapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import pers.xds.wtuapp.web.common.ResponseCode;
import pers.xds.wtuapp.web.common.ResponseTemplate;
import pers.xds.wtuapp.security.SecurityConstant;
import pers.xds.wtuapp.security.UserPrincipal;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.database.group.UpdateGroup;
import pers.xds.wtuapp.web.es.bean.EsCommodity;
import pers.xds.wtuapp.web.service.CommodityService;
import pers.xds.wtuapp.web.security.util.SecurityContextUtil;
import pers.xds.wtuapp.web.service.ServiceCode;
import pers.xds.wtuapp.web.service.ServiceCodeWrapper;

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
    public ResponseTemplate<Integer> createCommodity(@Validated Commodity commodity) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        commodity.setOwnerId(userPrincipal.getId());
        ServiceCodeWrapper<Integer> wrapper = commodityService.insertCommodity(commodity);
        ServiceCode code = wrapper.code;
        if (wrapper.isSuccess()) {
            return ResponseTemplate.success(wrapper.data);
        } else if (code == ServiceCode.RATE_LIMIT) {
            return ResponseTemplate.fail(ResponseCode.RATE_LIMIT, "您已达到每周商品发布数量上限, 该限制将在下周重置");
        }
        // code == ServiceCode.NOT_AVAILABLE
        return ResponseTemplate.fail(ResponseCode.RATE_LIMIT, "您发布的商品已经到达数量上限");
    }

    /**
     * 查询商品信息
     * @param id 商品id
     * @return 商品详细信息，可能为空
     */
    @GetMapping("/op/{id}")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<Commodity> queryCommodity(@PathVariable int id) {
        return ResponseTemplate.success(commodityService.queryCommodity(id));
    }

    /**
     * 锁定某个商品，并创建交易记录，表示买家想要购买这个商品。
     * @param id 商品id
     * @return 是否成功
     */
    @PostMapping("/op/{id}/lock")
    public ResponseTemplate<Integer> lockCommodity(@PathVariable int id,
                                        @RequestParam(value = "c", required = false, defaultValue = "1") int count,
                                        @RequestParam(value = "r", required = false) String remark) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        ServiceCodeWrapper<Integer> wrapper = commodityService.lockCommodity(id, userPrincipal.getId(), count, remark);
        ServiceCode code = wrapper.code;
        if (code == ServiceCode.SUCCESS) {
            return ResponseTemplate.success(wrapper.data);
        } else if (code == ServiceCode.RATE_LIMIT) {
            return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "您已到达每日的商品锁定次数上限，请明天再试");
        }else if (code == ServiceCode.CONCURRENT_ERROR){
            return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE, "服务器繁忙，请稍后再试");
        } else {
            return ResponseTemplate.fail(ResponseCode.NOT_AVAILABLE);
        }
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
    public ResponseTemplate<List<EsCommodity>> searchCommodityByName(@RequestParam("s") String search,
                                                                     @RequestParam(value = "z", required = false, defaultValue = "10") int size,
                                                                     @RequestParam(value = "p", required = false, defaultValue = "0") int page) {
        if (search.length() > MAX_SEARCH_SIZE) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST, null);
        }
        return ResponseTemplate.success(commodityService.searchCommodityByName(search, page, size));
    }

    /**
     * 获取用户正在售卖的商品数量
     */
    @GetMapping("selling_count")
    public ResponseTemplate<Integer> getSellingCount() {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(commodityService.querySellingCount(userPrincipal.getId()));
    }

    /**
     * 获取用户上传的商品
     */
    @GetMapping("uploaded")
    public ResponseTemplate<List<Commodity>> getUploadedCommodity(
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "s", required = false, defaultValue = "5") int size) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        return ResponseTemplate.success(commodityService.queryUserCommodity(userPrincipal.getId(), page, size));
    }

    /**
     * 更新商品
     */
    @PostMapping("/op/{cid}/update")
    public ResponseTemplate<Void> updateCommodity(@Validated(UpdateGroup.class) Commodity commodity, @PathVariable int cid) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        commodityService.updateCommodity(userPrincipal.getId(), cid, commodity);
        return ResponseTemplate.success();
    }

    /**
     * 下架商品
     */
    @PostMapping("/op/{cid}/close")
    public ResponseTemplate<Void> takeDownCommodity(@PathVariable int cid) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        commodityService.takeDownCommodity(userPrincipal.getId(), cid);
        return ResponseTemplate.success();
    }

    /**
     * 获取每日推荐
     * @param maxId 当前允许接收的最大id<p>
     *              例如将该值设为10，则返回的商品中最大id一定为10。
     *              为空时默认从最大开始选
     */
    @GetMapping("suggest")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<List<Commodity>> getRecommend(
            @RequestParam(required = false, value = "m") Integer maxId,
            @RequestParam(required = false, value = "s", defaultValue = "8") int size
    ) {
        final int maxSize = 8;
        if (size > maxSize) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        return ResponseTemplate.success(commodityService.getRecommend(maxId, size));
    }

}

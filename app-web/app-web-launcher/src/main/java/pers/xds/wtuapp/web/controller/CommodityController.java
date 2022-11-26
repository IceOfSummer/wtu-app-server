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
import pers.xds.wtuapp.web.es.bean.EsCommodity;
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
        int id = commodityService.insertCommodity(commodity);
        if (id == -1) {
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        } else if (id == -2) {
            return ResponseTemplate.fail(ResponseCode.COUNT_LIMIT);
        }
        return ResponseTemplate.success(id);
    }

    /**
     * 查询商品信息
     * @param id 商品id
     * @return 商品详细信息，可能为空
     */
    @GetMapping("{id}")
    @PreAuthorize(SecurityConstant.EL_PERMIT_ALL)
    public ResponseTemplate<Commodity> queryCommodity(@PathVariable int id) {
        return ResponseTemplate.success(commodityService.queryCommodity(id));
    }

    /**
     * 锁定某个商品，并创建交易记录，表示买家想要购买这个商品。
     * @param id 商品id
     * @return 是否成功
     */
    @PostMapping("/{id}/lock")
    public ResponseTemplate<Void> lockCommodity(@PathVariable int id,
                                        @RequestParam(value = "r", required = false) String remark) {
        UserPrincipal userPrincipal = SecurityContextUtil.getUserPrincipal();
        if (id == userPrincipal.getId()) {
            // 不能自己锁自己的商品
            return ResponseTemplate.fail(ResponseCode.BAD_REQUEST);
        }
        boolean result = commodityService.lockCommodity(id, userPrincipal.getId(), remark);
        if (result) {
            return ResponseTemplate.success();
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

}

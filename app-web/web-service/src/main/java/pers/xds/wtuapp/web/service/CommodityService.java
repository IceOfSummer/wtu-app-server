package pers.xds.wtuapp.web.service;

import org.springframework.lang.Nullable;
import pers.xds.wtuapp.web.database.bean.Commodity;
import pers.xds.wtuapp.web.es.bean.EsCommodity;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-09 18:01
 */
public interface CommodityService {

    /**
     * 插入货物
     * @param commodity 货物, <b>插入成功后会自动为其id字段赋值</b>
     * @return 新增货物的id<p>
     * - {@link ServiceCode#NOT_EXIST} 表示商品不存在<p>
     * - {@link ServiceCode#NOT_AVAILABLE} 表示用户已经达到商品上传上限<p>
     */
    ServiceCodeWrapper<Integer> insertCommodity(Commodity commodity);

    /**
     * 查询商品
     * @param commodityId 商品id
     * @return 商品信息，可能为空
     */
    @Nullable
    Commodity queryCommodity(int commodityId);


    /**
     * 锁定商品，并创建交易记录
     * @param commodityId 商品id
     * @param userId 用户id
     * @param count 要锁定多少个
     * @param remark 用户备注
     * @return
     * - {@link ServiceCode#BAD_REQUEST}表示商品数量不足;<p>
     * - {@link ServiceCode#NOT_EXIST}表示商品不存在;<p>
     * - {@link ServiceCode#NOT_AVAILABLE}表示商品不可用，如商品下架或者用户自己买自己的商品<p>
     * - {@link ServiceCode#CONCURRENT_ERROR}表示暂时锁定失败，稍后再试
     */
    ServiceCodeWrapper<Integer> lockCommodity(int commodityId, int userId, int count, String remark);

    /**
     * 搜索货物, 按照默认排序
     * @param commodityName 搜索内容
     * @param page 第几页
     * @return 相关商品内容
     */
    List<EsCommodity> searchCommodityByName(String commodityName, int page);

    /**
     * 搜索货物, 按照默认排序
     * @param commodityName 搜索内容
     * @param page 第几页
     * @param size 每页最大有几项
     * @return 相关商品内容
     */
    List<EsCommodity> searchCommodityByName(String commodityName, int page, int size);

    /**
     * 获取用户正在出售的商品数量
     * @param uid 用户id
     * @return 正在出售的商品数量
     */
    int querySellingCount(int uid);


}

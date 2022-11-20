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
     * @return 新增货物的id, 当返回-1时表示参数有误，当返回-2时，表示用户已经到达的商品发布的数量上限
     */
    int insertCommodity(Commodity commodity);

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
     * @param remark 用户备注
     * @return 是否锁定成功, 若返回false，表示商品已经被锁定了，<b>或者商品不存在</b>
     */
    boolean lockCommodity(int commodityId, int userId, String remark);

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

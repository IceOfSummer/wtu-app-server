package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.xds.wtuapp.web.database.bean.Commodity;

/**
 * @author DeSen Xu
 * @date 2022-09-09 11:27
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**
     * 获取用户当前正在出售的商品数量
     * @param uid 用户id
     * @return 正在出售的商品数量
     */
    @Select("SELECT COUNT(*) FROM commodity WHERE owner_id = #{uid} AND `status` = 0")
    Integer getSellingCount(@Param("uid") int uid);

    /**
     * 获取{@link Commodity#getCount()}, {@link Commodity#getStatus()}，{@link Commodity#getVersion()},
     * {@link Commodity#getPrice()}, {@link Commodity#getName()}, {@link Commodity#getTradeLocation()},
     * {@link Commodity#getPreviewImage()},
     * @param commodityId 商品id
     * @return commodity
     */
    Commodity selectSimpleInfo(@Param("id") int commodityId);

    /**
     * 更新商品剩余数量
     * @param commodityId 商品id
     * @param count 要更新的数量
     * @param version 乐观锁
     * @return 返回1表示更新成功，0表示失败
     */
    @Update("UPDATE commodity SET version = version + 1, `count` = #{count} WHERE commodity_id = #{id} AND version = #{version}")
    int updateCommodityCount(@Param("id") int commodityId, @Param("count") int count, @Param("version") int version);


    /**
     * 根据uid查询用户发布的货物，不会获取{@link Commodity#getVersion()}, {@link Commodity#getImages()}
     * @param uid 用户id
     * @param page 分页
     * @return 用户发布的货物
     */
    IPage<Commodity> selectCommodityByUid(@Param("uid") int uid, IPage<Commodity> page);

    /**
     * 更新货物信息
     * 仅可更新{@link Commodity#name}, {@link Commodity#price},
     *      * {@link Commodity#description}, {@link Commodity#tradeLocation}字段
     * @param commodity 货物
     * @param ownerId 货物主人id
     * @return 返回1表示操作成功
     */
    int updateCommodity(@Param("commodity") Commodity commodity, @Param("ownerId") int ownerId);
}

package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.xds.wtuapp.web.database.bean.Commodity;

import java.util.List;

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
     * 增加商品数量
     * @param commodityId 商品id
     * @param increment 要添加多少
     * @return 返回1表示成功
     */
    int incrementCommodityCount(@Param("cid") int commodityId, @Param("inc") int increment);

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
     * 更新货物信息. 这里更新数量时没有加锁，似乎可以忽略并发造成的影响。
     * 仅可更新{@link Commodity#name}, {@link Commodity#price}, {@link Commodity#count}
     *      {@link Commodity#description}, {@link Commodity#tradeLocation}字段
     * @param commodity 货物
     * @param commodityId 商品id
     * @param ownerId 货物主人id
     * @return 返回1表示操作成功
     */
    int updateCommodity(@Param("uid") int ownerId, @Param("cid") int commodityId, @Param("commodity") Commodity commodity);

    /**
     * 更新商品状态
     * @param uid 用户id
     * @param commodityId 商品id
     * @param status {@link Commodity#STATUS_ACTIVE}, {@link Commodity#STATUS_INACTIVE}
     */
    @Update("UPDATE commodity SET `status` = #{status} WHERE commodity_id = #{cid} AND owner_id = #{uid}")
    int updateCommodityStatus(@Param("uid") int uid, @Param("cid") int commodityId, @Param("status") int status);


    /**
     * 根据创建时间降序查询
     * @param maxId 最大id, 为空时默认从最大选
     * @param size 分页，每页最多几个
     * @return 查询到的商品, 只会获取{@link Commodity#commodityId}, {@link Commodity#ownerId}, {@link Commodity#name}
     * {@link Commodity#price}, {@link Commodity#previewImage}
     */
    List<Commodity> selectByTimeDesc(@Param("mid") Integer maxId, @Param("s") int size);

    /**
     * 如果商品数量为0并且{@link Commodity#autoTakeDown}为true，则下架该商品。
     * @param commodityId 商品id
     */
    void takeDownCommodityIfEmpty(@Param("cid") int commodityId);


}

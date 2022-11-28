package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    @Select("SELECT COUNT FROM commodity WHERE owner_id = #{uid} AND `status` = 0")
    Integer getSellingCount(@Param("uid") int uid);

    /**
     * 获取{@link Commodity#getCount()}, {@link Commodity#getStatus()}，{@link Commodity#getVersion()}，
     * {@link Commodity#getOwnerId()}字段
     * @param commodityId 商品id
     * @return commodity
     */
    @Select("SELECT `count`, `status`, version, owner_id FROM commodity WHERE commodity_id = #{id}")
    Commodity selectCountInfo(@Param("id") int commodityId);

    /**
     * 更新商品剩余数量
     * @param commodityId 商品id
     * @param count 要更新的数量
     * @param version 乐观锁
     * @return 返回1表示更新成功，0表示失败
     */
    @Update("UPDATE commodity SET version = version + 1, `count` = #{count} WHERE commodity_id = #{id} AND version = #{version}")
    int updateCommodity(@Param("id") int commodityId, @Param("count") int count, @Param("version") int version);

}

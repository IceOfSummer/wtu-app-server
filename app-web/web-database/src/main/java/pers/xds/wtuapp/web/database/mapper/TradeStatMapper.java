package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.xds.wtuapp.web.database.bean.TradeStat;

/**
* @author DeSen Xu
*/
@Mapper
public interface TradeStatMapper extends BaseMapper<TradeStat> {


    /**
     * 获取用户已经出售的商品数量
     * @param uid 用户id
     * @return 已经出售的商品数量
     */
    @Select("SELECT selling_count FROM trade_stat WHERE user_id = #{uid}")
    Integer selectSellingCount(@Param("uid") int uid);


    /**
     * 修改用户的商品出售数量
     * @param uid 用户id
     * @param updateCnt 要设置的值
     */
    @Update("UPDATE trade_stat SET selling_count = #{cnt} WHERE user_id = #{uid}")
    void modifySellingCount(@Param("uid") int uid, @Param("cnt") int updateCnt);
}

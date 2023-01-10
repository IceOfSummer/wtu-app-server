package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.xds.wtuapp.web.database.bean.TradeStat;

/**
* @author DeSen Xu
*/
@Mapper
public interface TradeStatMapper {


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

    /**
     * 将正在出售的商品数量减一
     * @param uid 用户id
     */
    @Update("UPDATE trade_stat SET selling_count = selling_count - 1 WHERE user_id = #{uid}")
    void decreaseSellingCount(@Param("uid") int uid);

    /**
     * 插入一条用户交易统计
     * @param uid 用户id
     * @param receiveCount 待收货数量
     * @param deliveryCount 待发货数量
     */
    void insert(@Param("uid") int uid, @Param("rc") int receiveCount, @Param("dc") int deliveryCount);

    /**
     * 插入一条用户交易统计，但只提供部分参数
     * @param userId 用户id
     * @param sellingCount 正在出售的商品数量
     */
    void insertWithSellingCountOnly(@Param("uid") int userId, @Param("sc") int sellingCount);

    /**
     * 获取用户交易统计
     * @param uid 用户id
     * @return 用户交易统计，只有 {@link TradeStat#income}, {@link TradeStat#expenditure},
     *  {@link TradeStat#receiveCount}, {@link TradeStat#deliveryCount} 字段
     */
    TradeStat selectUserTradeState(@Param("uid") int uid);

    /**
     * 修改用户待收货商品数量
     * @param uid 用户id
     * @param increment 增值，可以为负数
     * @return 返回1表示成功, 0表示不存在, 需要插入
     */
    int modifyReceiveCount(@Param("uid") int uid, @Param("inc") int increment);

    /**
     * 修改用户待发货商品数量
     * @param uid 用户id
     * @param increment 增值，可以为负数
     * @return 返回1表示成功, 0表示不存在, 需要插入
     */
    int modifyDeliveryCount(@Param("uid") int uid, @Param("inc") int increment);

}

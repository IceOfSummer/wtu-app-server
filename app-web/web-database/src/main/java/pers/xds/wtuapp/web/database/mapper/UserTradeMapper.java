package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.xds.wtuapp.web.database.bean.UserTrade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
* @author HuPeng
* @description 针对表【user_trade(用户交易记录表)】的数据库操作Mapper
* @createDate 2022-11-03 15:48:46
* @Entity pers.xds.wtuapp.web.database.bean.domain.UserTrade
*/
@Mapper
public interface UserTradeMapper extends BaseMapper<UserTrade> {

    /**
     * 添加用户交易记录，该操作会分别为卖家和买家添加记录
     * @param orderId 订单id
     * @param buyer 买家
     * @param seller 卖家
     */
    void addUserTrade(@Param("order_id") int orderId, @Param("buyer") int buyer, @Param("seller") int seller);

    /**
     * 获取用户当前激活的商品购买订单数
     * @param uid 用户id
     * @return 当前激活的商品购买订单数
     */
    @Select("SELECT COUNT(*) FROM user_trade WHERE user_id = #{uid} AND type = 0 AND `status` = 0")
    int selectUserBuyingCount(@Param("uid") int uid);

    /**
     * 标记交易成功
     * @param userId 用户id
     * @param orderId 订单id
     * @param type {@link UserTrade#getType()}
     * @param status {@link UserTrade#getStatus()}
     * @return 返回1表示成功，0表示失败
     */
    int modifyTradeStatus(
            @Param("userId") int userId,
            @Param("orderId") int orderId,
            @Param("type") int type,
            @Param("status") int status
    );

}





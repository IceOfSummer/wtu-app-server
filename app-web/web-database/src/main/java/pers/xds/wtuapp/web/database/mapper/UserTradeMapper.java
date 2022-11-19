package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * 添加用户交易记录
     * @param orderId 订单id
     * @param buyer 买家
     * @param seller 卖家
     */
    void addUserTrade(@Param("order_id") int orderId, @Param("buyer") int buyer, @Param("seller") int seller);

}





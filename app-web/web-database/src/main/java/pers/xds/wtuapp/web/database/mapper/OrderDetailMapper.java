package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.view.OrderDetail;


/**
 * @author DeSen Xu
 * @date 2022-11-19 21:44
 */
@Mapper
public interface OrderDetailMapper {

    /**
     * 获取用户订单的简要信息
     * @param uid 用户id
     * @param status 要选哪个状态的订单，传null为全选
     * @param page 分页
     * @return 交易中的订单
     */
    IPage<OrderDetail> selectOrders(@Param("uid") int uid, @Param("status") Integer status, IPage<OrderDetail> page);

}

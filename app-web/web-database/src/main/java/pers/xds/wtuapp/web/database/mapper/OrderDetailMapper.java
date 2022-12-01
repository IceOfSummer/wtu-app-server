package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.view.OrderDetail;

import java.util.List;


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

    /**
     * 根据订单类型获取订单, <b>默认获取激活的订单</b>
     * @param uid 用户id
     * @param type 类型 {@link OrderDetail#type}
     * @return 订单信息，<b>type属性为null</b>
     */
    List<OrderDetail> selectActiveOrderByType(@Param("uid") int uid, @Param("type") int type);

    /**
     * 获取所有订单
     * @param uid 用户id
     * @param page 分页
     * @return 订单信息
     */
    IPage<OrderDetail> selectAllOrder(@Param("uid") int uid, IPage<OrderDetail> page);

    /**
     * 获取用户所有的出售订单
     * @param uid 用户id
     * @param page 分页
     * @return 所有出售订单
     */
    IPage<OrderDetail> selectAllSoldOrder(@Param("uid") int uid, IPage<OrderDetail> page);

}

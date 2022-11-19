package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.database.view.OrderDetail;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-03 15:53
 */
public interface OrderService {

    /**
     * 获取用户交易记录(不管是否已经完成)
     * @param userid 用户id
     * @param page 第几页, 从0开始
     * @param size 每页最多显示多少行
     * @return 正在交易的商品记录
     */
    List<OrderDetail> getUserOrderDetails(int userid, int page, int size);

    /**
     * 获取用户当前正激活的订单
     * @param uid 用户id
     * @return 正激活的订单
     */
    List<OrderDetail> getUserActiveOrderDetails(int uid);

}

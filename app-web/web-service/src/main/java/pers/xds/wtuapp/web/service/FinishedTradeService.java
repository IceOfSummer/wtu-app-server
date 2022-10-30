package pers.xds.wtuapp.web.service;


import pers.xds.wtuapp.web.database.bean.Order;
import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-17 21:44
 */
public interface FinishedTradeService {




    /**
     * 获取用户的商品出售记录
     * @param userid 用户id
     * @param page 第几页
     * @param size 每页大小
     * @return 出售记录
     */
    List<Order> getSoldOrder(int userid, int page, int size);

    /**
     * 获取用户的商品出售<b>简要</b>记录
     * @param userid 用户id
     * @param page 第几页
     * @param size 每页大小
     * @return 出售记录
     */
    List<Order> getSoldOrderSimply(int userid, int page, int size);
}

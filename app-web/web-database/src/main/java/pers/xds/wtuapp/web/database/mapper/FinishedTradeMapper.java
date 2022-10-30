package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.FinishedTrade;
import pers.xds.wtuapp.web.database.bean.Order;

/**
 * @author DeSen Xu
 */
@Mapper
public interface FinishedTradeMapper extends BaseMapper<FinishedTrade> {

    /**
     * 获取用户商品出售记录
     * @param iPage 分页
     * @param userId 用户id
     * @return 出售记录
     */
    IPage<FinishedTrade> getSoldRecord(IPage<FinishedTrade> iPage, @Param("userId") int userId);

    /**
     * 获取用户出售的商品
     * @param iPage 分页
     * @param userId 用户id
     * @return 出售的商品
     */
    IPage<Order> getSoldOrder(IPage<Order> iPage, @Param("userId") int userId);

    /**
     * 获取用户出售商品的简要记录
     * @param iPage 分页
     * @param userId 用户id
     * @return 出售商品的简要记录
     */
    IPage<Order> getSoldOrderSimply(IPage<Order> iPage, @Param("userId") int userId);
}

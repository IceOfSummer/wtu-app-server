package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.xds.wtuapp.web.database.bean.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author HuPeng
* @description 针对表【order(订单表)】的数据库操作Mapper
* @date  2022-11-03 15:53:00
* @Entity pers.xds.wtuapp.web.database.bean.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {


}





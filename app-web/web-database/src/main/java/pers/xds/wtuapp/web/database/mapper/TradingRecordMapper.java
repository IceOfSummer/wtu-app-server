package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.TradingRecord;

/**
* @author DeSen Xu
*/
@Mapper
public interface TradingRecordMapper extends BaseMapper<TradingRecord> {

    /**
     * 根据顾客id获取当前正在交易的记录
     * @param page 分页
     * @param customerId 顾客id
     * @return 交易记录
     */
    IPage<TradingRecord> getTradingRecordByCustomerId(IPage<TradingRecord> page, @Param("customerId") int customerId);


}

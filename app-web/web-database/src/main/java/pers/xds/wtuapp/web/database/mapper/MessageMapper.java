package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.Message;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-10-31 15:27
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {


    /**
     * 查询离线消息
     * @param uid 用户id
     * @param receivedId 消息id的最小值(只会返回消息id<b>大于</b>该值的消息)
     * @return 离线消息，一次最多获取50条
     */
    List<Message> queryOfflineMessage(@Param("uid") int uid, @Param("receivedId") int receivedId);

}

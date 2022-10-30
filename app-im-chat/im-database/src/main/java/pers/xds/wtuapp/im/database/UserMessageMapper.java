package pers.xds.wtuapp.im.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.im.database.bean.Message;

import java.util.List;

/**
 * @author HuPeng
 * @date 2022-10-29 18:36
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<Message> {

    /**
     * 获取用户未接收的消息，每次最多获取100条
     * @param userid 用户id
     * @return 未接收的消息
     */
    List<Message> queryUnreceivedMessage(@Param("userid") int userid);

}

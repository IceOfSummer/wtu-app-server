package pers.xds.wtuapp.im.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.im.database.bean.UserMessage;

import java.util.List;

/**
 * @author HuPeng
 * @date 2022-10-29 18:36
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessage> {

    /**
     * 获取用户未接收的消息
     * @param userid 用户id
     * @return 未接收的消息
     */
    List<UserMessage> queryUnreceivedMessage(@Param("userid") int userid);

}

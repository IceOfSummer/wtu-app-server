package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.UserMessage;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-03 17:05
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

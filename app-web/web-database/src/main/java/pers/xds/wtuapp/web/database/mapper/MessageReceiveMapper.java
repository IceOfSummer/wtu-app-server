package pers.xds.wtuapp.web.database.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.web.database.bean.MessageReceive;

/**
 * @author DeSen Xu
 * @date 2022-09-03 17:22
 */
@Mapper
public interface MessageReceiveMapper extends BaseMapper<MessageReceive> {

    /**
     * 更新用户已经接收到的消息
     * @param userId 用户id
     * @param receivedId 接收到的消息
     */
    void updateUserReceivedMsgId(@Param("userId") int userId, @Param("receivedId") int receivedId);
}

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
     * 将目标用户的unreceived_id字段加一
     * @param userId 目标用户id
     */
    void increaseMessageCounter(@Param("userId") int userId);

    /**
     * 更新用户已经接收到的消息
     * @param userId 用户id
     * @param receivedId 接收到的消息
     * @return 返回1表示成功，若返回0，可能有如下几种原因:
     *  - 未找到该用户
     *  - receivedId大于unreceivedId
     */
    Integer updateUserReceivedMsgId(@Param("userId") int userId, @Param("receivedId") int receivedId);
}

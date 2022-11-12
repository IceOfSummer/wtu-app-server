package pers.xds.wtuapp.im.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.database.bean.MessageReceive;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-11-10 17:30
 */
@Mapper
public interface MessageReceiveMapper extends BaseMapper<MessageReceive> {

    /**
     * 将用户received_id字段加1
     * @param uid 用户id
     */
    void increaseReceivedId(@Param("uid") int uid);

    /**
     * 将两个用户的received_id字段加1
     * @param uid1 用户id
     * @param uid2 用户id
     */
    @Update("UPDATE message_receive SET received_id = received_id + 1 WHERE uid IN (#{uid1},#{uid2})")
    void increaseReceivedIdTwo(@Param("uid1") int uid1, @Param("uid2") int uid2);

    /**
     * 获取用户消息接收id
     * @param uid 哪个用户
     * @return 当前接收id，可能为null
     */
    @Select("SELECT received_id FROM message_receive WHERE uid = #{uid}")
    Integer selectReceivedId(@Param("uid") int uid);

    /**
     * 选择两个人的接收id，使用悲观锁
     * @param uid1 用户id1
     * @param uid2 用户id2
     * @return 接收信息
     */
    @Select("SELECT * FROM message_receive WHERE uid IN (#{uid1}, #{uid2}) FOR UPDATE")
    List<MessageReceive> selectTwo(@Param("uid1") int uid1, @Param("uid2") int uid2);

    /**
     * 插入俩新实体
     * @param uid1 用户id
     * @param uid2 用户id
     */
    @Insert("INSERT INTO message_receive(uid) VALUES(#{uid1}),(#{uid2})")
    void insertTwo(@Param("uid1") int uid1, @Param("uid2") int uid2);
}

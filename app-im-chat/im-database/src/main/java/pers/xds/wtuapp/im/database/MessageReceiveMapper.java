package pers.xds.wtuapp.im.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.xds.wtuapp.im.database.bean.MessageReceive;

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
     * 获取用户消息接收id
     * @param uid 哪个用户
     * @return 当前接收id，可能为null
     */
    @Select("SELECT received_id FROM message_receive WHERE uid = #{uid}")
    Integer selectReceivedId(@Param("uid") int uid);
}

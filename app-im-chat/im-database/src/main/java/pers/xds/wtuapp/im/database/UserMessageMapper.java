package pers.xds.wtuapp.im.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.xds.wtuapp.im.database.bean.Message;

import java.util.List;


/**
 * @author HuPeng
 * @date 2022-10-29 18:36
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<Message> {

    /**
     * 插入两条消息, 一条是给自己的，另外一条是给别人发的
     * @param message 消息内容，请使用该构造器{@link Message#Message(Integer, Integer, String)}，该对象的uid参数应该为接收者id，
     *               而to参数应该为发送者的id
     * @param receiveId 消息接受者的下次信息id
     * @param sendId 消息发送者的下次信息id
     */
    void insertSelfAndTargetMessage(@Param("msg") Message message,
                                    @Param("receiveId") int receiveId,
                                    @Param("sendId") int sendId);


    /**
     * 选择一个范围内的消息
     * @param uid 用户id
     * @param start 开始id(包括)
     * @param end 结束id(不包括)
     * @return 消息
     */
    @Select("SELECT * FROM message WHERE uid = #{uid} AND msg_id >= #{start} AND msg_id <= #{end}")
    List<Message> selectRange(@Param("uid") int uid, @Param("start") int start, @Param("end") int end);

}

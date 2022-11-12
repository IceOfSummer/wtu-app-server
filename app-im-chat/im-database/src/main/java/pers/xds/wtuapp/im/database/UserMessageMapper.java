package pers.xds.wtuapp.im.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.xds.wtuapp.im.database.bean.Message;


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


}

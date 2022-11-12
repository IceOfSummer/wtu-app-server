package pers.xds.wtuapp.im.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.im.database.bean.Message;


/**
 * @author DeSen Xu
 * @date 2022-09-03 16:39
 */
@Service
public interface ChatService {

    /**
     * 保存消息, 消息会在发送者和接收者两边都会保存
     * @param message 消息内容，请使用该构造器{@link Message#Message(Integer, Integer, String)}，该对象的uid参数应该为接收者id，
     *                而to参数应该为发送者的id
     * @param sync 是否需要同步. 若用户在线，则应该设置为true以加快消息漏发时的恢复。<b>只会同步接收者的消息</b>
     * @return 消息id, 第一个为发送者的消息id，第二个为接受者的消息id
     */
    Pair<Integer, Integer> saveMessage(Message message, boolean sync);

}

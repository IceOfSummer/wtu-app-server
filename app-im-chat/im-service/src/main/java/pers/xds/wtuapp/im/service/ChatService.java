package pers.xds.wtuapp.im.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.im.database.bean.Message;

import java.util.List;


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

    /**
     * 同步消息, <b>该方法仅适合start和end的差值较小时使用</b>，一般用于同步在线消息
     * <p>
     * <b>不一定</b>会返回范围内的所有消息，若漏掉了消息，只会是从start开始的一段<b>连续的</b>消息
     * @param uid 用户id
     * @param start 从哪个消息id开始(包括)
     * @param end 从哪个消息id结束(不包括)
     * @return 消息内容
     */
    List<Message> syncOnlineMessage(int uid, int start, int end);


    /**
     * 同步消息，用于同步离线消息，<b>适合start和end差值较大的时候</b>。一定会返回所有的消息，除非不存在
     * @param uid 用户id
     * @param start 从哪个消息id开始(包括)
     * @param end 从哪个消息id结束(不包括)
     * @return 消息内容
     */
    List<Message> syncOfflineMessage(int uid, int start, int end);

    /**
     * 获取当前用户最大已保存的消息id
     * @param uid 用户id
     * @return 最大id
     */
    int queryMaxMessageId(int uid);

}

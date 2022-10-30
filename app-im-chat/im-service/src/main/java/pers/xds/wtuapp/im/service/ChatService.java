package pers.xds.wtuapp.im.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pers.xds.wtuapp.im.database.bean.MessageReceive;
import pers.xds.wtuapp.im.database.bean.Message;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2022-09-03 16:39
 */
@Service
public interface ChatService {

    /**
     * 获取由于离线未能收到的消息<p/>
     * 由于用户可能由于网络等各种原因没有收到消息，不应该在调用此方法后立即修改用户未接收的消息id
     * 而是应该由用户主动确认接收，并调用服务{@link ChatService#messageReceiveAck}来修改用户未接收的消息id
     * @param userid 用户id
     * @return 没有收到的消息
     */
    List<Message> getUnreceivedMessage(int userid);

    /**
     * 表示用户已经确认收到消息id为<code>receivedId</code>为止(包含)，之前所有的消息
     * @param userId 用户id
     * @param receivedId 用户在received之前的消息都已经收到了
     * @throws IllegalArgumentException receivedId大于unreceivedId
     */
    void messageReceiveAck(int userId, int receivedId) throws IllegalArgumentException;

    /**
     * 获取消息接收状况
     * @param userid 用户id
     * @return 消息接收状况
     */
    @NonNull
    MessageReceive getMessageReceiveStatus(int userid);

    /**
     * 保存离线消息
     * @param content 消息内容
     * @param sender 谁发的
     * @param to 发给谁
     * @throws NoSuchElementException 未找到该消息对应该用户
     */
    void saveOfflineMessage(String content, int sender, int to) throws NoSuchElementException;

}

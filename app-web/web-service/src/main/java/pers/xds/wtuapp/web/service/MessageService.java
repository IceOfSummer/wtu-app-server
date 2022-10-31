package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.database.bean.Message;

import java.util.List;

/**
 * @author HuPeng
 * @date 2022-10-31 15:08
 */
public interface MessageService {

    /**
     * 用户id
     * @param uid 用户id
     * @param receivedId 消息id的最小值(只会返回消息id<b>大于</b>该值的消息)
     * @return 离线消息，一次最多获取50条
     */
    List<Message> getOfflineMessage(int uid, int receivedId);

    /**
     * 用户主动标记小于等于{@param receivedId}的消息已经收到
     * @param uid 用户id
     * @param receivedId 已接收的消息id
     */
    void markMessageReceived(int uid, int receivedId);

    /**
     * 获取消息接收状态
     * @param uid 用户id
     * @return 消息接收状态
     */
    int getMessageReceivedId(int uid);

}

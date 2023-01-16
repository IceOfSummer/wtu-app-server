package pers.xds.wtuapp.web.service;

import pers.xds.wtuapp.web.database.bean.MessageTip;

import java.util.List;

/**
 * 消息提醒服务
 * @author DeSen Xu
 * @date 2023-01-11 13:57
 */
public interface MessageTipService {

    /**
     * 发送消息提醒。该方法为<b>异步</b>操作
     * @param messageTip 消息提醒
     */
    void sendTipMessage(MessageTip messageTip);


    /**
     * 查询用户消息提醒
     * @param uid 用户id
     * @param minId 最小的消息id(不包括)
     * @return 消息提醒
     */
    List<MessageTip> queryMessageTip(int uid, int minId);

}

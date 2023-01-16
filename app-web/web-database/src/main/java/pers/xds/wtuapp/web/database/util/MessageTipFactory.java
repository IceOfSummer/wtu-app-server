package pers.xds.wtuapp.web.database.util;

import pers.xds.wtuapp.web.database.bean.MessageTip;
import pers.xds.wtuapp.web.database.view.CommodityTipPayload;
import pers.xds.wtuapp.web.database.view.MessageReplyPayload;

/**
 * @author DeSen Xu
 * @date 2023-01-11 14:13
 */
public class MessageTipFactory {

    /**
     * 新建社区回复类型的消息
     * @return 消息提醒
     */
    public static MessageTip newReplyMessageTip(int receiver, String title, String message, MessageReplyPayload payload) {
        MessageTip messageTip = getBaseMessageTip(receiver, title, message, MessageTip.TYPE_COMMUNITY_REPLY);
        messageTip.setPayload(Jackson.toJsonString(payload));
        return messageTip;
    }

    /**
     * 新建商品相关信息
     * @param receiver 接收者
     * @param title 标题
     * @param message 消息内容(商品名称)
     * @param payload 额外负载
     * @return 消息提醒
     */
    public static MessageTip newCommodityActionTip(int receiver, String title, String message, CommodityTipPayload payload) {
        return getBaseMessageTip(receiver, title, message, MessageTip.TYPE_TRADE_ACTION);
    }

    private static MessageTip getBaseMessageTip(int receiver, String title, String message, int type) {
        MessageTip messageTip = new MessageTip();
        messageTip.setReceiver(receiver);
        messageTip.setTitle(title);
        messageTip.setMessage(message);
        messageTip.setType(type);
        return messageTip;
    }

}

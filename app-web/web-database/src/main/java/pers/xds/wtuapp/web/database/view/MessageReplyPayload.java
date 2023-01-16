package pers.xds.wtuapp.web.database.view;

/**
 * 消息回复的payload
 * @see pers.xds.wtuapp.web.database.bean.MessageTip#payload
 * @author DeSen Xu
 * @date 2023-01-11 14:23
 */
public class MessageReplyPayload {

    /**
     * 社区消息/评论id
     */
    private int messageId;

    /**
     * 哪个用户发的
     */
    private int from;


    public MessageReplyPayload(int messageId, int from) {
        this.messageId = messageId;
        this.from = from;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}

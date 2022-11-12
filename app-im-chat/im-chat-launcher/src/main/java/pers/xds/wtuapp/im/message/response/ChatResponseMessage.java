package pers.xds.wtuapp.im.message.response;

import pers.xds.wtuapp.im.message.ResponseMessage;
import pers.xds.wtuapp.im.proto.ChatResponseMessageProto;

/**
 * 用于解析消息的响应
 * @author DeSen Xu
 * @date 2022-09-03 18:19
 */
public class ChatResponseMessage extends ResponseMessage {

    /**
     * 要发送的消息
     */
    private ChatResponseMessageProto.ChatResponseMessage message;

    public static final byte MESSAGE_TYPE = 4;

    private ChatResponseMessage() {
        super(MESSAGE_TYPE, SERVER_REQUEST_CODE);
    }

    public ChatResponseMessage(pers.xds.wtuapp.im.database.bean.Message message, int msgId) {
        this();
        this.message = ChatResponseMessageProto.ChatResponseMessage
                .newBuilder()
                .setMsgId(msgId)
                .setFrom(message.getTo())
                .setContent(message.getContent())
                .setCreateTime(message.getCreateTime())
                .build();
    }

    /**
     * <b>DEBUG使用</b>
     */
    public ChatResponseMessage(ChatResponseMessageProto.ChatResponseMessage message) {
        this();
        this.message = message;
    }

    @Override
    public byte[] encode() {
        return message.toByteArray();
    }

    public ChatResponseMessageProto.ChatResponseMessage getMessage() {
        return message;
    }

    public void setMessage(ChatResponseMessageProto.ChatResponseMessage message) {
        this.message = message;
    }
}

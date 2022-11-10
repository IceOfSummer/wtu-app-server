package pers.xds.wtuapp.im.message;

import pers.xds.wtuapp.im.proto.ChatResponseMessageProto;

/**
 * 用于解析消息的响应
 * @author DeSen Xu
 * @date 2022-09-03 18:19
 */
public class ChatResponseMessage extends pers.xds.wtuapp.im.message.Message {

    /**
     * 要发送的消息
     */
    private ChatResponseMessageProto.ChatResponseMessage message;

    public static final byte MESSAGE_TYPE = 4;

    private ChatResponseMessage() {
        super(MESSAGE_TYPE, SERVER_REQUEST_CODE);
    }

    public ChatResponseMessage(ChatRequestMessage message, int sender, int msgId) {
        this();
        this.message = ChatResponseMessageProto.ChatResponseMessage
                .newBuilder()
                .setMsgId(msgId)
                .setFrom(message.getFrom())
                .setFrom(sender)
                .setContent(message.getMessage())
                .setCreateTime(System.currentTimeMillis())
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

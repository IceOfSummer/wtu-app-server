package pers.xds.wtuapp.im.message;

import pers.xds.wtuapp.im.proto.OnlineChatMessageProto;

/**
 * 用于解析消息的响应
 * @author DeSen Xu
 * @date 2022-09-03 18:19
 */
public class ChatResponseMessage extends pers.xds.wtuapp.im.message.Message {

    /**
     * 要发送的消息
     */
    private OnlineChatMessageProto.OnlineChatMessage message;

    public static final byte MESSAGE_TYPE = 126;

    private ChatResponseMessage(short requestId) {
        super(MESSAGE_TYPE, requestId);
    }

    public ChatResponseMessage(ChatRequestMessage message, int sender, short requestId) {
        this(requestId);
        this.message = OnlineChatMessageProto.OnlineChatMessage
                .newBuilder()
                .setTo(message.getTo())
                .setFrom(sender)
                .setContent(message.getMessage())
                .setCreateTime(System.currentTimeMillis())
                .build();
    }

    /**
     * <b>DEBUG使用</b>
     */
    public ChatResponseMessage(OnlineChatMessageProto.OnlineChatMessage message) {
        this(REQUEST_ID_ZERO);
        this.message = message;
    }

    @Override
    public byte[] encode() {
        return message.toByteArray();
    }

    public OnlineChatMessageProto.OnlineChatMessage getMessage() {
        return message;
    }

    public void setMessage(OnlineChatMessageProto.OnlineChatMessage message) {
        this.message = message;
    }


}

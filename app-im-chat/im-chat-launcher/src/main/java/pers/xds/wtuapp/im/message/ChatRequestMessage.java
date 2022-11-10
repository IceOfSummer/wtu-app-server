package pers.xds.wtuapp.im.message;


import pers.xds.wtuapp.im.proto.ChatRequestMessageProto;

/**
 * 私聊请求消息
 * @author DeSen Xu
 * @date 2022-09-02 12:41
 */
public class ChatRequestMessage extends Message {

    private final int from;

    private final String message;

    public static final byte MESSAGE_TYPE = 1;

    /**
     * Parser专用
     */
    public ChatRequestMessage(ChatRequestMessageProto.ChatRequestMessage chatMessageAttach) {
        super(MESSAGE_TYPE, (short) 0);
        this.message = chatMessageAttach.getContent();
        this.from = chatMessageAttach.getTo();
    }

    public ChatRequestMessage(String message, int from, short requestId) {
        super(MESSAGE_TYPE, requestId);
        this.message = message;
        this.from = from;
        this.requestId = requestId;
    }

    @Override
    public byte[] encode() {
        return ChatRequestMessageProto.ChatRequestMessage
                .newBuilder()
                .setContent(message)
                .setTo(from)
                .build()
                .toByteArray();
    }

    public int getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ChatRequestMessage{" +
                "to=" + from +
                ", message='" + message + '\'' +
                '}';
    }
}

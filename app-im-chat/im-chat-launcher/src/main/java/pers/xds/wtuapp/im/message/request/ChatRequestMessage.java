package pers.xds.wtuapp.im.message.request;


import pers.xds.wtuapp.im.message.RequestMessage;
import pers.xds.wtuapp.im.proto.ChatRequestMessageProto;

/**
 * 私聊请求消息
 * @author DeSen Xu
 * @date 2022-09-02 12:41
 */
public class ChatRequestMessage extends RequestMessage {

    private final int to;

    private final String message;

    public static final byte MESSAGE_TYPE = 1;

    public ChatRequestMessage(ChatRequestMessageProto.ChatRequestMessage chatMessageAttach) {
        this.message = chatMessageAttach.getContent();
        this.to = chatMessageAttach.getTo();
    }

    @Override
    public byte getMessageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public byte[] encode() {
        return ChatRequestMessageProto.ChatRequestMessage
                .newBuilder()
                .setContent(message)
                .setTo(to)
                .build()
                .toByteArray();
    }

    public int getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ChatRequestMessage{" +
                "to=" + to +
                ", message='" + message + '\'' +
                '}';
    }
}

package pers.xds.wtuapp.im.message.response;

import pers.xds.wtuapp.im.message.ResponseMessage;
import pers.xds.wtuapp.im.proto.ChatResponseMessageProto;
import pers.xds.wtuapp.im.database.bean.Message;
import java.util.List;

/**
 * 回复多个消息内容
 * @author DeSen Xu
 * @date 2022-11-12 16:22
 */
public class MultiChatResponseMessage extends ResponseMessage {

    private final ChatResponseMessageProto.ChatResponseMessageGroup messageGroup;

    public static final byte MESSAGE_TYPE = 6;

    public MultiChatResponseMessage(short requestId, List<Message> messages) {
        super(requestId);
        ChatResponseMessageProto.ChatResponseMessageGroup.Builder builder = ChatResponseMessageProto.ChatResponseMessageGroup.newBuilder();
        for (Message message : messages) {
            if (message != null) {
                builder.addMessages(buildSingle(message));
            }
        }
        this.messageGroup = builder.build();
    }

    /**
     * for test
     */
    public MultiChatResponseMessage(ChatResponseMessageProto.ChatResponseMessageGroup messageGroup) {
        super(MESSAGE_TYPE);
        this.messageGroup = messageGroup;
    }

    private ChatResponseMessageProto.ChatResponseMessage buildSingle(Message message) {
        return ChatResponseMessageProto.ChatResponseMessage.newBuilder()
                .setMsgId(message.getMsgId())
                .setContent(message.getContent())
                .setCreateTime(message.getCreateTime())
                .setFrom(message.getTo())
                .setType(message.getType())
                .build();
    }


    @Override
    public byte getMessageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public byte[] encode() {
        return messageGroup.toByteArray();
    }

    @Override
    public String toString() {
        return "MultiChatResponseMessage{" +
                "messageGroup=" + messageGroup.getMessagesList() +
                '}';
    }
}

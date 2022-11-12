package pers.xds.wtuapp.im.message;

import pers.xds.wtuapp.im.proto.ChatResponseMessageProto;
import pers.xds.wtuapp.im.database.bean.Message;
import java.util.List;

/**
 * 回复多个消息内容
 * @author DeSen Xu
 * @date 2022-11-12 16:22
 */
public class MultiChatResponseMessage extends pers.xds.wtuapp.im.message.Message {

    private final ChatResponseMessageProto.ChatResponseMessageGroup messageGroup;

    public static final byte MESSAGE_TYPE = 6;

    public MultiChatResponseMessage(short requestId, List<Message> messages) {
        super(MESSAGE_TYPE, requestId);
        ChatResponseMessageProto.ChatResponseMessageGroup.Builder builder = ChatResponseMessageProto.ChatResponseMessageGroup.newBuilder();
        for (Message message : messages) {
            builder.addMessages(buildSingle(message));
        }
        this.messageGroup = builder.build();
    }

    private ChatResponseMessageProto.ChatResponseMessage buildSingle(Message message) {
        return ChatResponseMessageProto.ChatResponseMessage.newBuilder()
                .setMsgId(message.getMsgId())
                .setContent(message.getContent())
                .setCreateTime(message.getCreateTime())
                .setFrom(message.getTo())
                .build();
    }


    @Override
    public byte[] encode() {
        return messageGroup.toByteArray();
    }
}

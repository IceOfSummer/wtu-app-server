package pers.xds.wtuapp.im.message;

import pers.xds.wtuapp.im.database.bean.UserMessage;
import pers.xds.wtuapp.im.proto.UserMessageProto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <b>测试用</b>, 用于解析消息的响应
 * @author DeSen Xu
 * @date 2022-09-03 18:19
 */
public class ChatResponseMessage extends Message{

    private List<UserMessage> messages;


    public static final byte MESSAGE_TYPE = 126;

    private ChatResponseMessage(short requestId) {
        super(MESSAGE_TYPE, requestId);
    }

    public ChatResponseMessage(List<UserMessage> messages, short requestId) {
        this(requestId);
        this.messages = messages;
    }

    public ChatResponseMessage(UserMessage singleMessage, short requestId) {
        this(requestId);
        this.messages = Collections.singletonList(singleMessage);
    }

    public ChatResponseMessage(ChatRequestMessage message, int sender, short requestId) {
        this(requestId);
        this.messages = Collections.singletonList(new UserMessage(-1, message.getTo(), sender, message.getMessage()));
    }

    /**
     * <b>DEBUG使用</b>
     */
    public ChatResponseMessage(UserMessageProto.UserMessageGroup messageGroup) {
        this((short) 0);
        this.messages = new ArrayList<>(messageGroup.getMessageCount());
        for (UserMessageProto.UserMessage userMessage : messageGroup.getMessageList()) {
            messages.add(castProtoToMessage(userMessage));
        }
    }

    @Override
    public byte[] encode() {
        if (messages.isEmpty()) {
            return new byte[0];
        }
        UserMessageProto.UserMessageGroup.Builder builder = UserMessageProto.UserMessageGroup.newBuilder();
        for (UserMessage message : messages) {
            builder.addMessage(buildMessage(message));
        }
        return builder.build().toByteArray();
    }

    private UserMessageProto.UserMessage buildMessage(UserMessage message) {
        return UserMessageProto.UserMessage
                .newBuilder()
                .setMsgId(message.getMsgId())
                .setTo(message.getMsgTo())
                .setContent(message.getContent())
                .setCreateTime(message.getCreateTime() == null ? System.currentTimeMillis() : message.getCreateTime().getTime())
                .setFrom(message.getMsgFrom())
                .build();
    }

    private UserMessage castProtoToMessage(UserMessageProto.UserMessage message) {
        return new UserMessage(message.getMsgId(), message.getTo(), message.getFrom(), message.getContent());
    }

    public List<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<UserMessage> messages) {
        this.messages = messages;
    }


}

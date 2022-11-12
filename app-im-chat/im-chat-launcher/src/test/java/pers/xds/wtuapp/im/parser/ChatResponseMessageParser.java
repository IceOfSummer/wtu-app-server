package pers.xds.wtuapp.im.parser;

import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.message.response.ChatResponseMessage;
import pers.xds.wtuapp.im.proto.ChatResponseMessageProto;

/**
 * @author DeSen Xu
 * @date 2022-11-12 22:07
 */
public class ChatResponseMessageParser implements MessageParser<ChatResponseMessage> {
    @Override
    public ChatResponseMessage parseFrom(byte[] bytes) throws Exception {
        return new ChatResponseMessage(ChatResponseMessageProto.ChatResponseMessage.parseFrom(bytes));
    }

    @Override
    public byte getMessageType() {
        return ChatResponseMessage.MESSAGE_TYPE;
    }
}

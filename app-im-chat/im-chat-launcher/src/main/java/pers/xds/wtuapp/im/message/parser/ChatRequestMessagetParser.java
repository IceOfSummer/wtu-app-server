package pers.xds.wtuapp.im.message.parser;

import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.message.request.ChatRequestMessage;
import pers.xds.wtuapp.im.proto.ChatRequestMessageProto;

/**
 * @author DeSen Xu
 * @date 2022-11-12 21:56
 */
public class ChatRequestMessagetParser implements MessageParser<ChatRequestMessage> {

    @Override
    public ChatRequestMessage parseFrom(byte[] bytes) throws Exception {
        return new ChatRequestMessage(ChatRequestMessageProto.ChatRequestMessage.parseFrom(bytes));
    }

    @Override
    public byte getMessageType() {
        return ChatRequestMessage.MESSAGE_TYPE;
    }

}

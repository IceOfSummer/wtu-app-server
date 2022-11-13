package pers.xds.wtuapp.im.parser;

import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.message.response.MultiChatResponseMessage;
import pers.xds.wtuapp.im.proto.ChatResponseMessageProto;


/**
 * @author DeSen Xu
 * @date 2022-11-13 21:43
 */
public class MultiChatResponseMessageParser implements MessageParser<MultiChatResponseMessage> {
    @Override
    public MultiChatResponseMessage parseFrom(byte[] bytes) throws Exception {
        // 0 for test
        return new MultiChatResponseMessage(ChatResponseMessageProto.ChatResponseMessageGroup.parseFrom(bytes));
    }

    @Override
    public byte getMessageType() {
        return MultiChatResponseMessage.MESSAGE_TYPE;
    }
}

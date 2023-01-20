package pers.xds.wtuapp.im.message.parser;

import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.message.request.IdleRequestMessage;

/**
 * @author DeSen Xu
 * @date 2023-01-19 22:20
 */
public class IdleMessageParser implements MessageParser<IdleRequestMessage> {


    @Override
    public IdleRequestMessage parseFrom(byte[] bytes) throws Exception {
        return new IdleRequestMessage();
    }

    @Override
    public byte getMessageType() {
        return IdleRequestMessage.MESSAGE_TYPE;
    }

}

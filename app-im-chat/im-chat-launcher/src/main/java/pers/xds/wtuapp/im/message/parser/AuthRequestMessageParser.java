package pers.xds.wtuapp.im.message.parser;

import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.message.request.AuthRequestMessage;


/**
 * @author DeSen Xu
 * @date 2022-11-12 21:55
 */
public class AuthRequestMessageParser implements MessageParser<AuthRequestMessage> {

    @Override
    public AuthRequestMessage parseFrom(byte[] bytes) throws Exception {
        return new AuthRequestMessage(new String(bytes));
    }

    @Override
    public byte getMessageType() {
        return AuthRequestMessage.MESSAGE_TYPE;
    }

}

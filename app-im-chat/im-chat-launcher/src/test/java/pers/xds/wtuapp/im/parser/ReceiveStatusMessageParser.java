package pers.xds.wtuapp.im.parser;

import pers.xds.wtuapp.im.message.response.ReceiveStatusMessage;
import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.proto.ReceiveStatusMessageProto;

/**
 * @author DeSen Xu
 * @date 2022-11-12 21:33
 */
public class ReceiveStatusMessageParser implements MessageParser<ReceiveStatusMessage> {

    @Override
    public ReceiveStatusMessage parseFrom(byte[] bytes) throws Exception {
        return new ReceiveStatusMessage(ReceiveStatusMessageProto.ReceiveStatus.parseFrom(bytes));
    }

    @Override
    public byte getMessageType() {
        return ReceiveStatusMessage.MESSAGE_TYPE;
    }

}

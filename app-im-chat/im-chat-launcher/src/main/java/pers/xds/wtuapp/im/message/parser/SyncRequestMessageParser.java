package pers.xds.wtuapp.im.message.parser;

import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.message.request.SyncRequestMessage;
import pers.xds.wtuapp.im.proto.SyncRequestMessageProto;

/**
 * @author DeSen Xu
 * @date 2022-11-12 21:58
 */
public class SyncRequestMessageParser implements MessageParser<SyncRequestMessage> {

    @Override
    public SyncRequestMessage parseFrom(byte[] bytes) throws Exception {
        return new SyncRequestMessage(SyncRequestMessageProto.SyncRequestMessage.parseFrom(bytes));
    }

    @Override
    public byte getMessageType() {
        return SyncRequestMessage.MESSAGE_TYPE;
    }
}

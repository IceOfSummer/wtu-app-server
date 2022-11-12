package pers.xds.wtuapp.im.parser;

import pers.xds.wtuapp.im.message.MessageParser;
import pers.xds.wtuapp.im.message.response.ServerResponseMessage;
import pers.xds.wtuapp.im.proto.ServerResponseMessageProto;

/**
 * @author DeSen Xu
 * @date 2022-11-12 22:04
 */
public class ServerResponseMessageParser implements MessageParser<ServerResponseMessage> {
    @Override
    public ServerResponseMessage parseFrom(byte[] bytes) throws Exception {
        ServerResponseMessageProto.ServerResponseMessage message = ServerResponseMessageProto.ServerResponseMessage.parseFrom(bytes);
        return new ServerResponseMessage(message.getSuccess(), (short) message.getRequestId(), message.getData());
    }

    @Override
    public byte getMessageType() {
        return ServerResponseMessage.MESSAGE_TYPE;
    }
}

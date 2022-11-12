package pers.xds.wtuapp.im.factory;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.ServerResponseMessage;
import pers.xds.wtuapp.im.message.common.MessageFactory;
import pers.xds.wtuapp.im.proto.ServerResponseMessageProto;

/**
 * @author DeSen Xu
 * @date 2022-10-31 0:10
 */
public class ServerResponseMessageFactory implements MessageFactory<ServerResponseMessage> {

    @Override
    @NonNull
    public ServerResponseMessage createInstance(byte[] data) {
        try {
            ServerResponseMessageProto.ServerResponseMessage serverResponseMessage = ServerResponseMessageProto.ServerResponseMessage.parseFrom(data);
            return new ServerResponseMessage(serverResponseMessage.getSuccess(), (short) serverResponseMessage.getRequestId(), serverResponseMessage.getData());
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }
}



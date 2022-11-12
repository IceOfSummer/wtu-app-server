package pers.xds.wtuapp.im.message.response;

import pers.xds.wtuapp.im.message.ResponseMessage;
import pers.xds.wtuapp.im.proto.ServerResponseMessageProto;

/**
 * 服务器用于响应时的消息
 * @author DeSen Xu
 * @date 2022-10-31 0:14
 */
public class ServerResponseMessage extends ResponseMessage {

    private final ServerResponseMessageProto.ServerResponseMessage message;

    public static final byte MESSAGE_TYPE = 2;

    public ServerResponseMessage(boolean success, short requestId, String data) {
        super(MESSAGE_TYPE, requestId);
        ServerResponseMessageProto.ServerResponseMessage.Builder builder = ServerResponseMessageProto.ServerResponseMessage.newBuilder()
                .setSuccess(success)
                .setRequestId(requestId);
        if (data != null) {
            builder.setData(data);
        }
        this.message = builder.build();
    }

    public ServerResponseMessage(boolean success, short requestId) {
        this(success, requestId, null);
    }

    public ServerResponseMessage(short requestId) {
        this(true, requestId, null);
    }

    public ServerResponseMessage(short requestId, String data) {
        this(true, requestId, data);
    }

    @Override
    public byte[] encode() {
        return message.toByteArray();
    }

    @Override
    public String toString() {
        return "ServerResponseMessage[" + message + "]";
    }

}

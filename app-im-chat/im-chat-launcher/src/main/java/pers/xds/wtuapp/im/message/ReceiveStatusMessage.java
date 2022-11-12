package pers.xds.wtuapp.im.message;

import pers.xds.wtuapp.im.proto.ReceiveStatusMessageProto;

/**
 * 接收状态消息，发送给客户端用于同步离线消息
 * @see QueryReceiveStatusMessage 使用该消息发起同步请求
 * @author DeSen Xu
 * @date 2022-11-12 18:14
 */
public class ReceiveStatusMessage extends Message{

    public static final byte MESSAGE_TYPE = 8;

    private final ReceiveStatusMessageProto.ReceiveStatus status;

    public ReceiveStatusMessage(int receivedId) {
        this(ReceiveStatusMessageProto.ReceiveStatus.newBuilder()
                .setReceivedId(receivedId)
                .build());
    }

    public ReceiveStatusMessage(ReceiveStatusMessageProto.ReceiveStatus status) {
        super(MESSAGE_TYPE, SERVER_REQUEST_CODE);
        this.status =  status;
    }

    @Override
    public byte[] encode() {
        return status.toByteArray();
    }

    @Override
    public String toString() {
        return status.toString();
    }
}

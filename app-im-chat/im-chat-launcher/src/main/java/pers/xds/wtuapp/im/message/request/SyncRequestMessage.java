package pers.xds.wtuapp.im.message.request;

import pers.xds.wtuapp.im.message.RequestMessage;
import pers.xds.wtuapp.im.proto.SyncRequestMessageProto;

/**
 * 在线同步消息请求
 * @author DeSen Xu
 * @date 2022-11-12 16:09
 */
public class SyncRequestMessage extends RequestMessage {

    public static final byte MESSAGE_TYPE = 5;

    private final SyncRequestMessageProto.SyncRequestMessage message;

    public SyncRequestMessage(SyncRequestMessageProto.SyncRequestMessage syncRequestMessage) {
        this.message = syncRequestMessage;
    }

    @Override
    public byte getMessageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public byte[] encode() {
        return message.toByteArray();
    }

    public int getStart() {
        return message.getStart();
    }

    public int getEnd() {
        return message.getEnd();
    }

    public boolean isOffline() {
        return message.getOffline();
    }
}

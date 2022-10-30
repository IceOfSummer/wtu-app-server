package pers.xds.wtuapp.im.message;


import pers.xds.wtuapp.im.database.bean.MessageReceive;
import pers.xds.wtuapp.im.proto.MsgReceiveProto;

/**
 * 用于响应用户当前消息读取状态
 * @author DeSen Xu
 * @date 2022-09-07 22:16
 */
public class ReceiveStatusResponseMessage extends Message {

    private final int receivedId;

    private final int unreceivedId;

    /**
     * 测试用
     */
    public static final byte MESSAGE_TYPE = 125;

    public ReceiveStatusResponseMessage(short requestId, MessageReceive receive) {
        super(MESSAGE_TYPE, requestId);
        this.receivedId = receive.getReceiveId();
        this.unreceivedId = receive.getUnreceivedId();
    }

    /**
     * <b>测试用构造器</b> 提供消息解码
     */
    public ReceiveStatusResponseMessage(MsgReceiveProto.MessageReceive receive) {
        super(MESSAGE_TYPE, REQUEST_ID_ZERO);
        this.receivedId = receive.getReceivedId();
        this.unreceivedId = receive.getUnreceivedId();
    }

    @Override
    public byte[] encode() {
        return MsgReceiveProto.MessageReceive
                .newBuilder()
                .setReceivedId(receivedId)
                .setUnreceivedId(unreceivedId)
                .build()
                .toByteArray();
    }

    public int getReceivedId() {
        return receivedId;
    }

    public int getUnreceivedId() {
        return unreceivedId;
    }

    @Override
    public String toString() {
        return "ReceiveStatusResponseMessage{" +
                "receivedId=" + receivedId +
                ", unreceivedId=" + unreceivedId +
                '}';
    }
}

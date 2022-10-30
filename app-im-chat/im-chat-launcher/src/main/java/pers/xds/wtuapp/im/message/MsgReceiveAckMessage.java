package pers.xds.wtuapp.im.message;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.common.MessageFactory;

/**
 * 该消息表示用户确认已经收到了消息id在receivedId之前的所有消息
 * @author DeSen Xu
 * @date 2022-09-08 16:24
 */
public class MsgReceiveAckMessage extends Message {

    public static final byte MESSAGE_TYPE = 6;

    private final int receivedId;

    /**
     * 测试用
     */
    public MsgReceiveAckMessage(short requestId, int receivedId) {
        super(MESSAGE_TYPE, requestId);
        this.receivedId = receivedId;
    }

    private MsgReceiveAckMessage(int receivedId) {
        super(MESSAGE_TYPE, REQUEST_ID_ZERO);
        this.receivedId = receivedId;
    }

    @Override
    public byte[] encode() {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (receivedId & 0xff);
        bytes[2] = (byte) (receivedId >> 8 & 0xff);
        bytes[1] = (byte) (receivedId >> 16 & 0xff);
        bytes[0] = (byte) (receivedId >> 24 & 0xff);
        return bytes;
    }

    public static class Factory implements MessageFactory<MsgReceiveAckMessage> {

        @Override
        @NonNull
        public MsgReceiveAckMessage createInstance(byte[] data) {
            final int expectedArrLength = 4;
            if (data.length != expectedArrLength) {
                throw new IllegalArgumentException();
            }
            int receivedId = 0;
            for (byte datum : data) {
                receivedId <<= 8;
                receivedId |= datum;
            }
            return new MsgReceiveAckMessage(receivedId);
        }
    }

    public int getReceivedId() {
        return receivedId;
    }
}

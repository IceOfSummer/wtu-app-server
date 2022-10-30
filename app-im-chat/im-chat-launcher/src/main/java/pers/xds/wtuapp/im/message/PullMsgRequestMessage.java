package pers.xds.wtuapp.im.message;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.common.MessageFactory;

/**
 * 请求获取未读消息, 无数据体
 * @author DeSen Xu
 * @date 2022-09-05 18:57
 */
public class PullMsgRequestMessage extends Message {

    public static final byte MESSAGE_TYPE = 4;

    /**
     * Parser专用
     */
    private PullMsgRequestMessage() {
        super(MESSAGE_TYPE, REQUEST_ID_ZERO);
    }

    public PullMsgRequestMessage(short requestId) {
        super(MESSAGE_TYPE, requestId);
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    public static class PullMsgRequestMessageFactory implements MessageFactory<PullMsgRequestMessage> {

        @Override
        @NonNull
        public PullMsgRequestMessage createInstance(byte[] data) {
            return new PullMsgRequestMessage();
        }

    }

}

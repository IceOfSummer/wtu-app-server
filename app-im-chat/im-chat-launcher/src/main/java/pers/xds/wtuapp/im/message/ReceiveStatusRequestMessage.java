package pers.xds.wtuapp.im.message;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.common.MessageFactory;

/**
 * 用户拉取消息接收状态请求
 * @author DeSen Xu
 * @date 2022-09-07 18:36
 */
public class ReceiveStatusRequestMessage extends Message{

    public static final byte MESSAGE_TYPE = 5;

    public ReceiveStatusRequestMessage(short requestId) {
        super(MESSAGE_TYPE, requestId);
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }


    public static class Factory implements MessageFactory<ReceiveStatusRequestMessage> {

        @Override
        @NonNull
        public ReceiveStatusRequestMessage createInstance(byte[] data) {
            return new ReceiveStatusRequestMessage(REQUEST_ID_ZERO);
        }
    }
}

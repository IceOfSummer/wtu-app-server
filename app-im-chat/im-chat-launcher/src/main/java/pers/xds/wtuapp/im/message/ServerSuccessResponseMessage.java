package pers.xds.wtuapp.im.message;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.common.MessageFactory;

import java.nio.charset.StandardCharsets;

/**
 * 用于发送简单的服务器提醒
 * @author DeSen Xu
 * @date 2022-09-03 15:15
 */
public class ServerSuccessResponseMessage extends Message {

    private final byte[] message;

    public static final byte MESSAGE_TYPE = 2;

    private ServerSuccessResponseMessage(byte[] message) {
        super(MESSAGE_TYPE, REQUEST_ID_ZERO);
        this.message = message;
    }

    public ServerSuccessResponseMessage(String message, short messageId) {
        super(MESSAGE_TYPE, messageId);
        this.message = message.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] encode() {
        return message;
    }


    public static class Factory implements MessageFactory<ServerSuccessResponseMessage> {

        @Override
        @NonNull
        public ServerSuccessResponseMessage createInstance(byte[] data) {
            return new ServerSuccessResponseMessage(data);
        }
    }

}

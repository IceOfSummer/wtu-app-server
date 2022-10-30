package pers.xds.wtuapp.im.message;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.common.MessageFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author DeSen Xu
 * @date 2022-09-04 17:56
 */
public class ServerFailResponseMessage extends Message{

    public static final byte MESSAGE_TYPE = 3;

    private final byte[] message;

    /**
     * Parser专用
     */
    private ServerFailResponseMessage(byte[] message) {
        super(MESSAGE_TYPE, REQUEST_ID_ZERO);
        this.message = message;
    }

    public ServerFailResponseMessage(String message, short requestId) {
        super(MESSAGE_TYPE, requestId);
        this.message = message.getBytes(StandardCharsets.UTF_8);
    }

    public static class Factory implements MessageFactory<ServerFailResponseMessage> {

        @Override
        @NonNull
        public ServerFailResponseMessage createInstance(byte[] data) {
            return new ServerFailResponseMessage(data);
        }
    }

    @Override
    public byte[] encode() {
        return message;
    }

    @Override
    public String toString() {
        return "ServerFailResponseMessage[message=\"" + new String(message) + "\"]";
    }
}

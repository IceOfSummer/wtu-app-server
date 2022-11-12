package pers.xds.wtuapp.im.message.request;

import org.springframework.lang.NonNull;
import pers.xds.wtuapp.im.message.RequestMessage;
import pers.xds.wtuapp.im.message.common.MessageFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 认证请求消息，直接将session原文附加在数据上
 * @author DeSen Xu
 * @date 2022-09-02 14:26
 */
public class AuthRequestMessage extends RequestMessage {

    public static final byte MESSAGE_TYPE = 0;

    private String session;


    public AuthRequestMessage(String session) {
        super(MESSAGE_TYPE);
        this.session = session;
    }

    public static class Factory implements MessageFactory<AuthRequestMessage> {

        @Override
        @NonNull
        public AuthRequestMessage createInstance(byte[] data) {
            return new AuthRequestMessage(new String(Base64.getDecoder().decode(data)));
        }
    }

    @Override
    public byte[] encode() {
        return session.getBytes(StandardCharsets.UTF_8);
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

}

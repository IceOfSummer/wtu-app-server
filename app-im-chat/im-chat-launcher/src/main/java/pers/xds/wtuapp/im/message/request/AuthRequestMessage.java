package pers.xds.wtuapp.im.message.request;

import pers.xds.wtuapp.im.message.RequestMessage;

import java.nio.charset.StandardCharsets;

/**
 * 认证请求消息，直接将session原文附加在数据上
 * @author DeSen Xu
 * @date 2022-09-02 14:26
 */
public class AuthRequestMessage extends RequestMessage {

    public static final byte MESSAGE_TYPE = 0;

    private final String session;


    public AuthRequestMessage(String session) {
        this.session = session;
    }

    @Override
    public byte getMessageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public byte[] encode() {
        return session.getBytes(StandardCharsets.UTF_8);
    }

    public String getSession() {
        return session;
    }

}

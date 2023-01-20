package pers.xds.wtuapp.im.message.request;

import pers.xds.wtuapp.im.message.RequestMessage;

/**
 * 心跳包
 * @author DeSen Xu
 * @date 2023-01-19 21:35
 */
public class IdleRequestMessage extends RequestMessage {

    public static final byte MESSAGE_TYPE = 9;

    @Override
    public byte getMessageType() {
        return MESSAGE_TYPE;
    }


}

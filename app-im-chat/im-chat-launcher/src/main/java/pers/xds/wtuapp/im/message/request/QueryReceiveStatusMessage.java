package pers.xds.wtuapp.im.message.request;

import pers.xds.wtuapp.im.message.RequestMessage;

/**
 * @author DeSen Xu
 * @date 2022-11-12 18:26
 */
public class QueryReceiveStatusMessage extends RequestMessage {

    public static final byte MESSAGE_TYPE = 7;


    @Override
    public byte getMessageType() {
        return MESSAGE_TYPE;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}

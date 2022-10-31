package pers.xds.wtuapp.im.message;

import pers.xds.wtuapp.im.message.common.ResponseCode;

/**
 * 服务器用于响应时的消息
 * @author DeSen Xu
 * @date 2022-10-31 0:14
 */
public class ServerResponseMessage extends Message{

    public static final byte MESSAGE_TYPE = 2;

    private final ResponseCode code;

    public ServerResponseMessage(ResponseCode code, short requestId) {
        super(MESSAGE_TYPE, requestId);
        this.code = code;
    }

    @Override
    public byte[] encode() {
        return code.getByteCode();
    }

    @Override
    public String toString() {
        return "ServerResponseMessage[code=\"" + code.getCode() + "\"]";
    }

}

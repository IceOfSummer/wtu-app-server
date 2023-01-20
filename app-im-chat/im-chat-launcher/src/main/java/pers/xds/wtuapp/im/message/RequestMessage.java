package pers.xds.wtuapp.im.message;

/**
 * 客户端向服务器发送请求的消息, 一般不需要提供请求id
 * @author DeSen Xu
 * @date 2022-11-12 21:46
 */
public abstract class RequestMessage extends Message {

    /**
     * 由客户端发送而来的消息，不需要服务器进行编码
     */
    @Override
    public byte[] encode() {
        return new byte[0];
    }

}

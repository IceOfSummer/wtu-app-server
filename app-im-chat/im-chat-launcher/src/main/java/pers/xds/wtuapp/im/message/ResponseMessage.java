package pers.xds.wtuapp.im.message;

/**
 * 服务器发送给客户端的消息
 * @author DeSen Xu
 * @date 2022-11-12 21:46
 */
public abstract class ResponseMessage extends Message{

    /**
     * 使用该构造器表示服务器主动向客户端推送信息
     */
    public ResponseMessage(byte orderType) {
        super(orderType, SERVER_REQUEST_CODE);
    }

    public ResponseMessage(byte orderType, short requestId) {
        super(orderType, requestId);
    }

}

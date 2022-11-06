package pers.xds.wtuapp.im.message;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 消息基类<p/>
 * 消息基本格式
 * <pre/>
 * +---------+----------+---------+---------+---------+--------+
 * |  魔 数  |  版本号   | 消息类型 | 请求序号 | 消息长度 |  消息内容 |
 * | 0xAC66 |  1byte   |  1byte  |  2byte  |  4byte  |   ?byte  |
 * +---------+----------+---------+---------+---------+--------+
 * @see pers.xds.wtuapp.im.protocol.MessageCodec 消息基类的编解码
 * @author DeSen Xu
 * @date 2022-09-02 11:44
 */
public abstract class Message {

    /**
     * 魔数
     */
    public static final short MAGIC_NUMBER = (short) 0xAC66;

    /**
     * 协议版本号
     */
    public static final byte VERSION = 1;

    /**
     * 消息类型
     */
    protected byte orderType;

    /**
     * 当服务器主动给客户端发送消息时，使用该requestId来进行区分
     */
    public static final short SERVER_REQUEST_CODE = -1;

    /**
     * 请求序号
     */
    protected short requestId;

    /**
     * 构造一个消息，但不提供requestId
     * @see Message#Message(byte, short)
     * @deprecated 为防止忘填requestId, 请使用{@link Message#Message(byte, short)}
     */
    public Message(byte orderType) {
        this.orderType = orderType;
    }

    /**
     * 构造一个消息
     * @param orderType 消息类型
     * @param requestId 请求序号
     */
    public Message(byte orderType, short requestId) {
        this.orderType = orderType;
        this.requestId = requestId;
    }

    public static class MessageFrameDecoder extends LengthFieldBasedFrameDecoder {

        public MessageFrameDecoder() {
            // max 1mb
            super(1024 * 1024, 6, 4);
        }

    }

    /**
     * 将消息的<b>数据体</b>进行编码
     * @return 编码后的消息
     */
    public abstract byte[] encode();

    public short getRequestId() {
        return requestId;
    }

    public void setRequestId(short requestId) {
        this.requestId = requestId;
    }

    public byte getOrderType() {
        return orderType;
    }

    public void setOrderType(byte orderType) {
        this.orderType = orderType;
    }

}

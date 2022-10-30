package pers.xds.wtuapp.im.message.common;

import pers.xds.wtuapp.im.message.Message;

/**
 * 无行为的Parser, 表示无需对正文序列化, 仅依靠消息类型即可获取相关信息<p/>
 * 使用MessageManager进行解析操作, 只需重写{@link NoActionParser#parseFrom(byte[])}方法即可
 * @see MessageDecoderManager
 * @author DeSen Xu
 * @date 2022-09-03 15:04
 */
public class NoActionParser<T extends Message> implements MessageParser<T> {

    private final MessageFactory<T> messageFactory;

    public NoActionParser(MessageFactory<T> messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public T parseFrom(byte[] bytes) {
        return messageFactory.createInstance(bytes);
    }
}

package pers.xds.wtuapp.im.message.common;


import pers.xds.wtuapp.im.exception.UnknownProtocolException;
import pers.xds.wtuapp.im.message.*;
import pers.xds.wtuapp.im.proto.ChatMessageProto;

/**
 * 用于管理各种消息的<b>解码</b>, 一般只针对请求类的消息<p/>
 * 解码器在静态代码块中静态注册
 * @author DeSen Xu
 * @date 2022-09-02 12:38
 */
public class MessageDecoderManager {

    private static final MessageParser<?>[] MESSAGE_CLAZZ = new MessageParser[Byte.MAX_VALUE];

    private MessageDecoderManager() {}

    static {
        registry(new NoActionParser<>(new AuthRequestMessage.Factory()), AuthRequestMessage.MESSAGE_TYPE);
        registry(new ProtobufToMessageParser<>(ChatMessageProto.ChatMessage.parser(), ChatRequestMessage::new), ChatRequestMessage.MESSAGE_TYPE);
    }

    public static void registry(MessageParser<?> parser, int id) {
        if (MESSAGE_CLAZZ[id] != null) {
            throw new IllegalArgumentException("该id已经被注册了: " + MESSAGE_CLAZZ[id]);
        }
        MESSAGE_CLAZZ[id] = parser;
    }

    public static Message decode(byte[] message, byte type) throws Exception {
        MessageParser<?> parser = MESSAGE_CLAZZ[type];
        if (parser == null) {
            throw new UnknownProtocolException("未知的消息类型");
        }
        return parser.parseFrom(message);
    }

}

package pers.xds.wtuapp.im.message.common;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import pers.xds.wtuapp.im.message.Message;

import java.util.function.Function;

/**
 * 将Protobuf生生成的对象转换为Message对象
 * @author DeSen Xu
 * @date 2022-09-04 17:14
 */
public class ProtobufToMessageParser<P> implements MessageParser<Message> {

    private final Parser<P> parser;

    private final Function<P, Message> parseFunction;

    public ProtobufToMessageParser(Parser<P> parser, Function<P, Message> parseFunction) {
        this.parser = parser;
        this.parseFunction = parseFunction;
    }

    @Override
    public Message parseFrom(byte[] bytes) throws InvalidProtocolBufferException {
        Object abstractMessage = parser.parseFrom(bytes);
        if (parseFunction == null) {
            return (Message) abstractMessage;
        }
        return this.parseFunction.apply(parser.parseFrom(bytes));
    }


}

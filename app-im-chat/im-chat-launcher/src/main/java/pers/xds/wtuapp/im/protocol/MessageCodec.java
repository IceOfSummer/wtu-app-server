package pers.xds.wtuapp.im.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.exception.UnknownProtocolException;
import pers.xds.wtuapp.im.message.Message;
import pers.xds.wtuapp.im.message.MessageDecoderManager;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-09-02 11:52
 */
@ChannelHandler.Sharable
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {

    private static final Logger log = LoggerFactory.getLogger(MessageCodec.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) {
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeShort(Message.MAGIC_NUMBER);
        buffer.writeByte(Message.VERSION);
        buffer.writeByte(msg.getMessageType());
        buffer.writeShort(msg.getRequestId());
        byte[] data = msg.encode();
        buffer.writeInt(data.length);
        buffer.writeBytes(data);
        out.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readShort() != Message.MAGIC_NUMBER) {
            throw new UnknownProtocolException();
        }
        // version code
        byteBuf.readByte();
        byte type = byteBuf.readByte();
        short id = byteBuf.readShort();
        ChannelAttrManager.saveRequestId(ctx, id);
        int len = byteBuf.readInt();
        byte[] data = new byte[len];
        byteBuf.readBytes(data);
        Message message = MessageDecoderManager.decode(data, type);
        message.setRequestId(id);
        log.debug("收到消息: {}", message);
        list.add(message);
    }

}

package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pers.xds.wtuapp.im.message.request.IdleRequestMessage;

/**
 * @author DeSen Xu
 * @date 2023-01-19 21:37
 */
public class IdleRequestHandler extends SimpleChannelInboundHandler<IdleRequestMessage> {

    @Override
    public boolean isSharable() {
        return true;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IdleRequestMessage msg) {

    }

}

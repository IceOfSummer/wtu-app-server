package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author DeSen Xu
 * @date 2022-10-31 14:52
 */
public class IdleEventHandler extends ChannelDuplexHandler {

    @Override
    public boolean isSharable() {
        return true;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
            }
        }
    }
}

package pers.xds.wtuapp.im.handler;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.message.ServerFailResponseMessage;

/**
 * 错误处理器, <b>可以被共享</b>
 * @author DeSen Xu
 * @date 2022-09-04 18:31
 */
public class ChannelExceptionHandler extends ChannelDuplexHandler {

    private static final Logger log = LoggerFactory.getLogger(ChannelExceptionHandler.class);

    @Override
    public boolean isSharable() {
        return true;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("", cause);
        postErrorMsg(ctx);
    }

    private void postErrorMsg(ChannelHandlerContext ctx) {
        if (ctx.channel().isWritable()) {
            Short id = ChannelAttrManager.getRequestId(ctx);
            if (id != null) {
                ctx.channel().writeAndFlush(new ServerFailResponseMessage("500", id));
            }
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        ctx.write(msg, promise.addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                Throwable cause = future.cause();
                postErrorMsg(ctx);
                log.error("", cause);
            }
        }));
    }
}

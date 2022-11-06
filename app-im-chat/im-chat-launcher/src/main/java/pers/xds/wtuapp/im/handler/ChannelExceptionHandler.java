package pers.xds.wtuapp.im.handler;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.message.ServerResponseMessage;
import pers.xds.wtuapp.im.message.common.ResponseCode;

/**
 * Channel异常处理器, 可以被共享
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
        if (ctx.channel().isWritable()) {
            Short id = ChannelAttrManager.getRequestId(ctx);
            if (id != null) {
                ctx.channel().writeAndFlush(new ServerResponseMessage(ResponseCode.SERVER_ERROR, id));
            }
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        promise.addListener(future -> {
            if (!future.isSuccess()) {
                Throwable cause = future.cause();
                log.error("while process message: " + msg + "failed", cause);
            }
        });
        super.write(ctx, msg, promise);
    }
}

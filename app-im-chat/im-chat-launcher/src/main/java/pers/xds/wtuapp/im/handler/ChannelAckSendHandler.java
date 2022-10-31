package pers.xds.wtuapp.im.handler;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.message.ServerResponseMessage;
import pers.xds.wtuapp.im.message.common.ResponseCode;

/**
 * 当消息处理完后返回处理完毕的提示, 同时当出现异常时捕获异常并提醒客户端, <b>不可以被共享</b>
 * @author DeSen Xu
 * @date 2022-09-04 18:31
 */
public class ChannelAckSendHandler extends ChannelDuplexHandler {

    private static final Logger log = LoggerFactory.getLogger(ChannelAckSendHandler.class);

    private boolean isSuccess;

    @Override
    public boolean isSharable() {
        return false;
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
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        ctx.write(msg, promise.addListener((ChannelFutureListener) future -> {
            isSuccess = future.isSuccess();
            if (!isSuccess) {
                Throwable cause = future.cause();
                log.error("", cause);
            }
        }));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        Short requestId = ChannelAttrManager.getRequestId(ctx);
        if (requestId == null) {
            return;
        }
        log.info("writing response message...");
        if (isSuccess) {
            ctx.writeAndFlush(new ServerResponseMessage(ResponseCode.SUCCESS, requestId));
        } else {
            ctx.writeAndFlush(new ServerResponseMessage(ResponseCode.SERVER_ERROR, requestId));
        }
    }
}

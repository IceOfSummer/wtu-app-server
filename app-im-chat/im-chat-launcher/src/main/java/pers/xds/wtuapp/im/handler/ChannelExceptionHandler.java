package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.message.ServerFailResponseMessage;

/**
 * 错误处理器
 * @author DeSen Xu
 * @date 2022-09-04 18:31
 */
@ChannelHandler.Sharable
public class ChannelExceptionHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ChannelExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("", cause);
        if (ctx.channel().isWritable()) {
            Short id = ChannelAttrManager.getRequestId(ctx);
            if (id != null) {
                ctx.channel().writeAndFlush(new ServerFailResponseMessage("服务器发生错误，请耐心等待开发人员修复QAQ", id));
                log.error("", cause);
            }
        }
    }
}

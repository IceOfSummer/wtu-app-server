package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;

import java.util.concurrent.TimeUnit;

/**
 * 处理用户刚刚进入通道，有如下任务:<p/>
 * 1. 添加定时任务，若在指定时间之后未能登录，则强行关闭用户连接
 * @author DeSen Xu
 * @date 2022-09-02 21:37
 */
@Component
public class ConnectActiveHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ConnectActiveHandler.class);

    private static final int EXPIRE_TIME = 10;

    @Override
    public boolean isSharable() {
        return true;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().schedule(() -> {
            UsernamePasswordAuthenticationToken token = ChannelAttrManager.getToken(ctx);
            if (token == null) {
                ctx.channel().close();
                log.debug("{}长时间未登录，已经关闭连接", ctx);
            }
        }, EXPIRE_TIME, TimeUnit.SECONDS);
        super.channelActive(ctx);

        // 标记通道是安全的
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                future -> {
                    ChannelAttrManager.setChannelSecure(ctx);
                    log.debug("Channel Active: " + ctx.channel());
                }
        );
    }
}

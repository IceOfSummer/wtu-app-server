package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import pers.xds.wtuapp.im.ChannelAttrManager;

import java.util.concurrent.TimeUnit;

/**
 * 处理用户刚刚进入通道，有如下任务:<p/>
 * 1. 添加定时任务，若在指定时间之后未能登录，则强行关闭用户连接
 * @author DeSen Xu
 * @date 2022-09-02 21:37
 */
public class ConnectActiveHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ConnectActiveHandler.class);

    public ConnectActiveHandler(int expireTime) {
        this.expireTime = expireTime;
    }

    public int expireTime;

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
        }, expireTime, TimeUnit.SECONDS);
        super.channelActive(ctx);
    }
}

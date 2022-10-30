package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.message.ReceiveStatusRequestMessage;
import pers.xds.wtuapp.im.message.ReceiveStatusResponseMessage;
import pers.xds.wtuapp.im.service.ChatService;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * 用于处理用户获取信息接收状态的拦截器
 * @author DeSen Xu
 * @date 2022-09-07 22:31
 */
@ChannelHandler.Sharable
@Component
public class ReceiveStatusRequestHandler extends SimpleChannelInboundHandler<ReceiveStatusRequestMessage> {

    private ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReceiveStatusRequestMessage msg) {
        UserPrincipal principal = ChannelAttrManager.getPrincipal(ctx);
        if (principal == null) {
            return;
        }
        Short requestId = ChannelAttrManager.getRequestId(ctx);
        ctx.writeAndFlush(new ReceiveStatusResponseMessage(requestId , chatService.getMessageReceiveStatus(principal.getId())));
    }

}

package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.message.request.QueryReceiveStatusMessage;
import pers.xds.wtuapp.im.message.response.ReceiveStatusMessage;
import pers.xds.wtuapp.im.service.ChatService;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * @author DeSen Xu
 * @date 2022-11-12 18:31
 */
@Component
public class ReceiveStatusMessageHandler extends SimpleChannelInboundHandler<QueryReceiveStatusMessage> {

    @Override
    public boolean isSharable() {
        return true;
    }

    private ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QueryReceiveStatusMessage msg) {
        UserPrincipal principal = ChannelAttrManager.getPrincipal(ctx);
        if (principal == null) {
            return;
        }
        ctx.writeAndFlush(new ReceiveStatusMessage(chatService.queryMaxMessageId(principal.getId()), msg.getRequestId()));
    }
}

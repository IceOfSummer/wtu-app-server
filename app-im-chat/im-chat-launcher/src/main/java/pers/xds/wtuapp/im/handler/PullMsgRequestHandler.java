package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.database.bean.UserMessage;
import pers.xds.wtuapp.im.message.ChatResponseMessage;
import pers.xds.wtuapp.im.message.PullMsgRequestMessage;
import pers.xds.wtuapp.im.service.ChatService;
import pers.xds.wtuapp.security.UserPrincipal;

import java.util.List;

/**
 * 用于处理用于拉取未读消息
 * @author DeSen Xu
 * @date 2022-09-06 21:58
 */
@ChannelHandler.Sharable
@Component
public class PullMsgRequestHandler extends SimpleChannelInboundHandler<PullMsgRequestMessage> {

    private static final Logger log = LoggerFactory.getLogger(PullMsgRequestHandler.class);

    private ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PullMsgRequestMessage msg) {
        UserPrincipal principal = ChannelAttrManager.getPrincipal(ctx);
        if (principal == null) {
            return;
        }
        List<UserMessage> unreceivedMessage = chatService.getUnreceivedMessage(principal.getId());
        if (!unreceivedMessage.isEmpty()) {
            try {
                ctx.channel().writeAndFlush(new ChatResponseMessage(unreceivedMessage, msg.getRequestId()));
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

}

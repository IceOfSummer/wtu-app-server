package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.message.MultiChatResponseMessage;
import pers.xds.wtuapp.im.message.SyncRequestMessage;
import pers.xds.wtuapp.im.service.ChatService;
import pers.xds.wtuapp.security.UserPrincipal;

import java.util.List;

/**
 * 在线消息同步拦截器。可以被共享
 * @author DeSen Xu
 * @date 2022-11-12 16:19
 */
@Component
public class SyncRequestMessageHandler extends SimpleChannelInboundHandler<SyncRequestMessage> {

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
    protected void channelRead0(ChannelHandlerContext ctx, SyncRequestMessage msg) {
        UserPrincipal principal = ChannelAttrManager.getPrincipal(ctx);
        if (principal == null) {
            return;
        }
        List<Message> messages;
        if (msg.isOffline()) {
            messages = chatService.syncOfflineMessage(principal.getId(), msg.getStart(), msg.getEnd());
        } else {
            messages = chatService.syncOnlineMessage(principal.getId(), msg.getStart(), msg.getEnd());
        }
        ctx.writeAndFlush(new MultiChatResponseMessage(msg.getRequestId(), messages));
    }

}

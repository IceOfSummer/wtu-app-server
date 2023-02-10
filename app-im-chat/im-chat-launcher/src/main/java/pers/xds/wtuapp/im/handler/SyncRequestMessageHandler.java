package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.message.response.MultiChatResponseMessage;
import pers.xds.wtuapp.im.message.request.SyncRequestMessage;
import pers.xds.wtuapp.im.service.ChatService;
import pers.xds.wtuapp.security.UserPrincipal;

import java.util.Collections;
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
            messages = chatService.syncOfflineMessage(principal.getId(), msg.getStart());
        } else {
            int end = msg.getEnd();
            if (end == -1) {
                messages = Collections.emptyList();
            } else {
                messages = chatService.syncOnlineMessage(principal.getId(), msg.getStart(), end);
            }
        }
        ctx.writeAndFlush(new MultiChatResponseMessage(msg.getRequestId(), messages));
    }

}

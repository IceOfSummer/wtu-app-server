package pers.xds.wtuapp.im.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.SocketChannelRecorder;
import pers.xds.wtuapp.im.message.ChatRequestMessage;
import pers.xds.wtuapp.im.message.ChatResponseMessage;
import pers.xds.wtuapp.im.message.ServerResponseMessage;
import pers.xds.wtuapp.im.message.common.ResponseCode;
import pers.xds.wtuapp.im.service.ChatService;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * @author DeSen Xu
 * @date 2022-09-02 14:56
 */
@Component
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    private static final Logger log = LoggerFactory.getLogger(ChatRequestMessageHandler.class);


    private ChatService chatService;

    private SocketChannelRecorder socketChannelRecorder;

    @Override
    public boolean isSharable() {
        return true;
    }


    @Autowired
    public void setSocketChannelRecorder(SocketChannelRecorder socketChannelRecorder) {
        this.socketChannelRecorder = socketChannelRecorder;
    }

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) {
        UserPrincipal principal = ChannelAttrManager.getPrincipal(ctx);
        if (principal == null) {
            // 用户未登录, 不做响应
            return;
        }

        // 从服务中拿Channel
        Channel ch = socketChannelRecorder.getChannel(msg.getTo());

        final short requestId = msg.getRequestId();
        if (ch == null) {
            // 发送离线消息
            chatService.saveOfflineMessage(msg.getMessage(), principal.getId(), msg.getTo());
            ctx.writeAndFlush(new ServerResponseMessage(requestId));
        } else {
            ch.writeAndFlush(new ChatResponseMessage(msg, principal.getId()), ch.newPromise().addListener(future -> {
                if (future.isSuccess()) {
                    ctx.writeAndFlush(new ServerResponseMessage(requestId));
                } else {
                    ctx.writeAndFlush(new ServerResponseMessage(ResponseCode.SERVER_ERROR, requestId));
                }
            }));
        }
        log.debug("用户id: {}给用户id: {} 发送了一条消息: {}", principal.getId(), msg.getTo(), msg.getMessage());
    }

}

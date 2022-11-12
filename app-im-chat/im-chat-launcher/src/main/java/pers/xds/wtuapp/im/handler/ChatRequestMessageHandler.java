package pers.xds.wtuapp.im.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.SocketChannelRecorder;
import pers.xds.wtuapp.im.database.bean.Message;
import pers.xds.wtuapp.im.message.request.ChatRequestMessage;
import pers.xds.wtuapp.im.message.response.ChatResponseMessage;
import pers.xds.wtuapp.im.message.response.ServerResponseMessage;
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
        if (principal == null || msg.getTo() == principal.getId()) {
            return;
        }
        // 从服务中拿Channel
        Channel ch = socketChannelRecorder.getChannel(msg.getTo());

        final short requestId = msg.getRequestId();
        // 保存消息
        boolean sync = ch != null;
        Message message = new Message(msg.getTo(), principal.getId(), msg.getMessage());
        Pair<Integer, Integer> pair = chatService.saveMessage(message, sync);
        if (sync) {
            // 发送在线消息
            ch.writeAndFlush(new ChatResponseMessage(message, pair.getSecond()), ch.newPromise().addListener(future -> {
                if (future.isSuccess()) {
                    ctx.writeAndFlush(new ServerResponseMessage(requestId, String.valueOf(pair.getFirst())));
                } else {
                    ctx.writeAndFlush(new ServerResponseMessage(false, requestId));
                }
            }));
        } else {
            ctx.writeAndFlush(new ServerResponseMessage(requestId, String.valueOf(pair.getFirst())));
        }
        log.debug("用户id: {}给用户id: {} 发送了一条消息: {}", principal.getId(), msg.getTo(), msg.getMessage());
    }

}

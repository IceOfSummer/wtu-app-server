package pers.xds.wtuapp.im.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.SocketChannelRecorder;
import pers.xds.wtuapp.im.database.bean.UserMessage;
import pers.xds.wtuapp.im.message.ChatRequestMessage;
import pers.xds.wtuapp.im.message.ChatResponseMessage;
import pers.xds.wtuapp.im.message.ServerFailResponseMessage;
import pers.xds.wtuapp.im.service.ChatService;
import pers.xds.wtuapp.security.UserPrincipal;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

/**
 * @author DeSen Xu
 * @date 2022-09-02 14:56
 */
@ChannelHandler.Sharable
@Component
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    private static final Logger log = LoggerFactory.getLogger(ChatRequestMessageHandler.class);

    private static final AttributeKey<WeakReference<Channel>> RECENT_TALK_KEY = AttributeKey.valueOf("recentTalk");


    private ChatService chatService;

    private SocketChannelRecorder socketChannelRecorder;

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
        // 先找缓存
        Attribute<WeakReference<Channel>> attr = ctx.channel().attr(RECENT_TALK_KEY);
        WeakReference<Channel> channelWeakReference = attr.get();
        Channel ch = null;
        if (channelWeakReference != null) {
            // 拿Channel缓存
            Channel channel = channelWeakReference.get();
            if (channel == null || ChannelAttrManager.getChannelUserId(channel) != msg.getTo()) {
                // Channel已经被清理 || 缓存的channel不是目标
                // 释放弱键
                attr.set(null);
            }
            ch = channel;
        }

        // 从服务中拿Channel
        if (ch == null) {
            Channel target = socketChannelRecorder.getChannel(msg.getTo());
            if (target != null) {
                ch = target;
                // 放缓存
                attr.set(new RecentTalkToChannel(target));
            }
        }

        UserMessage userMessage = new UserMessage(msg.getTo(), principal.getId(), msg.getMessage());
        if (ch == null || ch.isOpen()) {
            // 发送离线消息
            try {
                chatService.saveOfflineMessage(userMessage);
            } catch (NoSuchElementException e){
                ctx.channel().writeAndFlush(new ServerFailResponseMessage("目标用户不存在", ChannelAttrManager.getRequestId(ctx)));
            } catch (Exception e) {
                log.error("", e);
                ctx.channel().writeAndFlush(new ServerFailResponseMessage("消息发送失败", ChannelAttrManager.getRequestId(ctx)));
            }
        } else {
            try {
                ch.writeAndFlush(new ChatResponseMessage(msg, principal.getId(), msg.getRequestId()));
            } catch (Exception e) {
                // 发送失败，尝试发送离线消息
                log.error("", e);
                try {
                    chatService.saveOfflineMessage(userMessage);
                } catch (Exception ex) {
                    log.error("", ex);
                    ctx.channel().writeAndFlush(new ServerFailResponseMessage("消息发送失败", ChannelAttrManager.getRequestId(ctx)));
                }
            }
        }
        log.debug("用户id: {}给用户id: {} 发送了一条消息: {}", principal.getId(), msg.getTo(), msg.getMessage());
    }


    /**
     * 保存最近聊天的channel
     * <p>
     * 为了防止内存溢出，使用弱引用保存
     */
    private static class RecentTalkToChannel extends WeakReference<Channel> {

        public RecentTalkToChannel(Channel referent) {
            super(referent);
        }

    }
}

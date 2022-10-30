package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.message.MsgReceiveAckMessage;
import pers.xds.wtuapp.im.service.ChatService;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * 处理MsgReceiveAckMessage消息<p/>
 * 该操作不应该由用户控制，而是由APP在后台自动控制，<b>所以在操作完成后不会向客户端发送完成提醒</b>。
 * 若操作失败，则会主动通知客户端进行重试。
 * @author DeSen Xu
 * @date 2022-09-08 16:33
 */
@Component
@ChannelHandler.Sharable
public class MsgReceiveAckRequestHandler extends SimpleChannelInboundHandler<MsgReceiveAckMessage> {

    private ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgReceiveAckMessage msg) {
        UserPrincipal principal = ChannelAttrManager.getPrincipal(ctx);
        if (principal == null) {
            return;
        }
        chatService.messageReceiveAck(principal.getId(), msg.getReceivedId());
    }

}

package pers.xds.wtuapp.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.ChannelAttrManager;
import pers.xds.wtuapp.im.SocketChannelRecorder;
import pers.xds.wtuapp.im.message.request.AuthRequestMessage;
import pers.xds.wtuapp.im.message.response.ServerResponseMessage;
import pers.xds.wtuapp.im.service.ChatAuthService;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * 用于处理登录
 * 在用户成功登录后，调用服务保存Channel，同时使用ChannelAttrManager保存Token和userid
 * @author DeSen Xu
 * @date 2022-09-02 16:56
 */
@Component
public class AuthHandler extends SimpleChannelInboundHandler<AuthRequestMessage> {

    private ChatAuthService chatAuthService;

    private SocketChannelRecorder socketChannelRecorder;

    @Override
    public boolean isSharable() {
        return true;
    }

    @Autowired
    public void setSocketChannelRecorderImpl(SocketChannelRecorder socketChannelRecorder) {
        this.socketChannelRecorder = socketChannelRecorder;
    }
    private static final Logger log = LoggerFactory.getLogger(AuthHandler.class);

    @Autowired
    public void setChatAuthService(ChatAuthService chatAuthService) {
        this.chatAuthService = chatAuthService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AuthRequestMessage msg) {
        if (ChannelAttrManager.getPrincipal(ctx) != null) {
            // 已经登录了
            return;
        }
        UserPrincipal principal = chatAuthService.findUser(msg.getSession());
        if (principal == null) {
            log.debug("用户登录失败: " + msg);
        } else {
            ChannelAttrManager.savePrincipal(ctx, principal);

            socketChannelRecorder.saveChannel(principal.getId(), ctx.channel());
            log.debug("用户id: {}, 登录成功: {}", principal.getId(), msg);
            final short requestId = msg.getRequestId();
            ctx.writeAndFlush(new ServerResponseMessage(requestId));
        }
    }

}

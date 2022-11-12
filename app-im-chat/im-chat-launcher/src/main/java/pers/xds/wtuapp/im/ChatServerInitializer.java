package pers.xds.wtuapp.im;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.xds.wtuapp.im.handler.*;
import pers.xds.wtuapp.im.message.Message;
import pers.xds.wtuapp.im.protocol.MessageCodec;
import pers.xds.wtuapp.security.UserPrincipal;

/**
 * @author HuPeng
 * @date 2022-10-26 13:07
 */
@Component
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger log = LoggerFactory.getLogger(ChatServerInitializer.class);

    private static final MessageCodec MESSAGE_CODEC = new MessageCodec();

    private static final IdleEventHandler IDLE_EVENT_HANDLER = new IdleEventHandler();

    private static final ChannelExceptionHandler CHANNEL_EXCEPTION_HANDLER = new ChannelExceptionHandler();

    private SslContext sslContext;

    private AuthHandler authHandler;

    private ChatRequestMessageHandler chatMessageHandler;

    private ConnectActiveHandler connectActiveHandler;

    private SocketChannelRecorder socketChannelRecorder;

    private SyncRequestMessageHandler syncRequestMessageHandler;

    private ReceiveStatusMessageHandler receiveStatusMessageHandler;

    @Autowired
    public void setReceiveStatusMessageHandler(ReceiveStatusMessageHandler receiveStatusMessageHandler) {
        this.receiveStatusMessageHandler = receiveStatusMessageHandler;
    }

    @Autowired
    public void setSyncRequestMessageHandler(SyncRequestMessageHandler syncRequestMessageHandler) {
        this.syncRequestMessageHandler = syncRequestMessageHandler;
    }


    @Autowired
    public void setSocketChannelRecorder(SocketChannelRecorder socketChannelRecorder) {
        this.socketChannelRecorder = socketChannelRecorder;
    }

    @Autowired
    public void setSslContext(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    @Autowired
    public void setConnectActiveHandler(ConnectActiveHandler connectActiveHandler) {
        this.connectActiveHandler = connectActiveHandler;
    }

    @Autowired
    public void setChatMessageHandler(ChatRequestMessageHandler chatMessageHandler) {
        this.chatMessageHandler = chatMessageHandler;
    }

    @Autowired
    public void setAuthHandler(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(sslContext.newHandler(ch.alloc()))
                .addLast(new IdleStateHandler(1000, 0, 0))
                .addLast(IDLE_EVENT_HANDLER)
                .addLast(new Message.MessageFrameDecoder())
                .addLast(connectActiveHandler)
                .addLast(MESSAGE_CODEC)
                .addLast(authHandler)
                .addLast(chatMessageHandler)
                .addLast(syncRequestMessageHandler)
                .addLast(receiveStatusMessageHandler)
                .addLast(CHANNEL_EXCEPTION_HANDLER);

        ch.closeFuture().addListener(future -> {
            log.info("channel已关闭: " + ch);
            UserPrincipal principal = ChannelAttrManager.getPrincipal(ch);
            if (principal != null) {
                // 移除保存的channel, 防止内存溢出
                socketChannelRecorder.removeChannel(principal.getId());
            }
        });

    }

}

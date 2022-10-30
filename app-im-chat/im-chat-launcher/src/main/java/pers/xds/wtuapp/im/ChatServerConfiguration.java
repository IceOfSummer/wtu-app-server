package pers.xds.wtuapp.im;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

/**
 * @author DeSen Xu
 * @date 2022-09-02 11:13
 */
@Configuration
public class ChatServerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ChatServerConfiguration.class);

    private EventLoopGroup bossEventLoopGroup;

    private EventLoopGroup workerEventLoopGroup;

    @Value("${server.port}")
    private int serverPort;

    private ChatServerInitializer chatServerInitializer;

    @Autowired
    public void setChatServerInitializer(ChatServerInitializer chatServerInitializer) {
        this.chatServerInitializer = chatServerInitializer;
    }

    @PostConstruct
    private void initServer() throws InterruptedException {
        bossEventLoopGroup = new NioEventLoopGroup(1);
        workerEventLoopGroup = new NioEventLoopGroup();

        new ServerBootstrap().channel(NioServerSocketChannel.class)
                .group(bossEventLoopGroup, workerEventLoopGroup)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(chatServerInitializer)
                .bind(serverPort).sync();
        log.info("聊天服务器已开启，正在监听 {} 端口", serverPort);
    }

    public EventLoopGroup getBossEventLoopGroup() {
        return bossEventLoopGroup;
    }

    public EventLoopGroup getWorkerEventLoopGroup() {
        return workerEventLoopGroup;
    }
}

package pers.xds.wtuapp.im;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xds.wtuapp.im.message.Message;
import pers.xds.wtuapp.im.message.MessageDecoderManager;
import pers.xds.wtuapp.im.message.request.AuthRequestMessage;
import pers.xds.wtuapp.im.message.request.ChatRequestMessage;
import pers.xds.wtuapp.im.message.request.QueryReceiveStatusMessage;
import pers.xds.wtuapp.im.message.response.ChatResponseMessage;
import pers.xds.wtuapp.im.message.response.ServerResponseMessage;
import pers.xds.wtuapp.im.parser.ChatResponseMessageParser;
import pers.xds.wtuapp.im.parser.ReceiveStatusMessageParser;
import pers.xds.wtuapp.im.parser.ServerResponseMessageParser;
import pers.xds.wtuapp.im.proto.ChatRequestMessageProto;
import pers.xds.wtuapp.im.protocol.MessageCodec;
import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author DeSen Xu
 * @date 2022-09-03 18:50
 */
public class ServerSocketTest {

    private static final Logger log = LoggerFactory.getLogger(ServerSocketTest.class);

    public static final int PORT = 7001;

    short requestId = 0;

    static Scanner sc = new Scanner(System.in);

    public void connect(int port) throws InterruptedException, SSLException {
        MessageDecoderManager.registry(new ChatResponseMessageParser());
        MessageDecoderManager.registry(new ServerResponseMessageParser());
        MessageDecoderManager.registry(new ReceiveStatusMessageParser());

        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodec messageCodec = new MessageCodec();
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Channel channel = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(sslContext.newHandler(ch.alloc()));
                        ch.pipeline().addLast(loggingHandler);
                        ch.pipeline().addLast(new Message.MessageFrameDecoder());
                        ch.pipeline().addLast(messageCodec);
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ChatResponseMessage>() {

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) {
                                log.info(msg.getMessage().toString());
                            }
                        });
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ServerResponseMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ServerResponseMessage msg) {
                                log.info("{}, requestId={}", msg, msg.getRequestId());
                            }
                        });
                    }
                })
                .connect(new InetSocketAddress(port))
                .sync()
                .channel();

        channel.closeFuture().addListener(future -> {
            System.out.println("channel closed ");
            eventLoopGroup.shutdownGracefully();
            System.exit(0);
        });

        System.out.print("请输入Session: ");
        String session = sc.nextLine();
        channel.writeAndFlush(new AuthRequestMessage(session));
        while (true) {
            // 先让别的线程打印完，不然看不到菜单
            Thread.sleep(600);
            System.out.print("=============Menu=============\n" +
                    "1: 和某人私聊\n" +
                    "2: 获取消息接收状态\n" +
                    "==============================\n" +
                    "请输入: ");
            int order = sc.nextInt();
            if (order == 1) {
                chatWithSomeone(channel);
            } else if (order == 2) {
                queryReceiveStatus(channel);
            }
        }
    }

    public void chatWithSomeone(Channel channel) {
        // send msg
        System.out.print("要发给谁: ");
        int to = sc.nextInt();
        System.out.print("消息内容: ");
        sc.nextLine();
        String line = sc.nextLine();

        ChatRequestMessage chatRequestMessage = new ChatRequestMessage(ChatRequestMessageProto.ChatRequestMessage.newBuilder()
                .setContent(line)
                .setTo(to).build());
        chatRequestMessage.setRequestId(++requestId);
        channel.writeAndFlush(chatRequestMessage);
        log.info("发送消息: {}", chatRequestMessage);
    }

    public void queryReceiveStatus(Channel channel) {
        channel.writeAndFlush(new QueryReceiveStatusMessage());
    }

    public static void main(String[] args) throws Exception {
        new ServerSocketTest().connect(PORT);
    }


}

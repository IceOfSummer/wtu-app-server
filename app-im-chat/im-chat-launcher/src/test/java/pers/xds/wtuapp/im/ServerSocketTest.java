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
import pers.xds.wtuapp.im.message.*;
import pers.xds.wtuapp.im.message.common.MessageDecoderManager;
import pers.xds.wtuapp.im.message.common.ProtobufToMessageParser;
import pers.xds.wtuapp.im.proto.MsgReceiveProto;
import pers.xds.wtuapp.im.proto.UserMessageProto;
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

    public void connect(int port) throws InterruptedException, SSLException {
        MessageDecoderManager.registry(new ProtobufToMessageParser<>(UserMessageProto.UserMessageGroup.parser(), ChatResponseMessage::new), ChatResponseMessage.MESSAGE_TYPE);
        MessageDecoderManager.registry(new ProtobufToMessageParser<>(MsgReceiveProto.MessageReceive.parser(), ReceiveStatusResponseMessage::new), ReceiveStatusResponseMessage.MESSAGE_TYPE);
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodec messageCodec = new MessageCodec();
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
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
                                log.info(msg.getMessages().toString());
                            }
                        });
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ReceiveStatusResponseMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ReceiveStatusResponseMessage msg) {
                                log.info("{}", msg);
                            }
                        });
                    }
                })
                .connect(new InetSocketAddress(port))
                .sync()
                .channel();

        channel.closeFuture().addListener(future -> {
            System.out.println("channel closed ");
        });
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入Session: ");
        String session = sc.nextLine();
        channel.writeAndFlush(new AuthRequestMessage(++requestId, session));
        while (true) {
            // 先让别的线程打印完，不然看不到菜单
            Thread.sleep(600);
            System.out.print("=============Menu=============\n" +
                    "1: 和某人私聊\n" +
                    "2: 拉取未读消息\n" +
                    "3: 获取消息接收状态\n" +
                    "4: 确认接收消息\n" +
                    "==============================\n" +
                    "请输入: ");
            int order = sc.nextInt();
            if (order == 1) {
                chatWithSomeone(sc, channel);
            } else if (order == 2) {
                channel.writeAndFlush(new PullMsgRequestMessage(++requestId));
            } else if (order == 3) {
                channel.writeAndFlush(new ReceiveStatusRequestMessage(++requestId));
            } else if (order == 4) {
                sendMsgAck(sc, channel);
            }
        }
    }

    public void chatWithSomeone(Scanner sc, Channel channel) {
        // send msg
        System.out.print("要发给谁: ");
        int to = sc.nextInt();
        System.out.print("消息内容: ");
        sc.nextLine();
        String line = sc.nextLine();
        ChatRequestMessage chatRequestMessage = new ChatRequestMessage(line, to, ++requestId);
        channel.writeAndFlush(chatRequestMessage);
        log.info("发送消息: {}", chatRequestMessage);
    }

    public void sendMsgAck(Scanner sc, Channel channel) {
        System.out.print("已接收的消息id: ");
        int id = sc.nextInt();
        channel.writeAndFlush(new MsgReceiveAckMessage(++requestId, id));
    }

    public static void main(String[] args) throws Exception {
        new ServerSocketTest().connect(PORT);
    }


}

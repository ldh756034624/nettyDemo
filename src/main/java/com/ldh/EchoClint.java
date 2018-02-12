package com.ldh;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by itservice on 2018/1/5.
 */
public class EchoClint {

    private int port;


    public static void start(int port) throws InterruptedException {


        final EchoClientHandler echoClientHandler = new EchoClientHandler();
        final OutBoundHandler outBoundHandler = new OutBoundHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress("127.0.0.1",port)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline().addLast(echoClientHandler,outBoundHandler);
                        socketChannel.pipeline().addLast(outBoundHandler,echoClientHandler);
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect().sync();

//        channelFuture.channel().closeFuture().sync();

    }

    public static void main(String[] args) throws InterruptedException {
        start(10086);
    }
}

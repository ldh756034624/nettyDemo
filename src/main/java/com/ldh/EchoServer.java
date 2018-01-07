package com.ldh;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by itservice on 2018/1/5.
 */
public class EchoServer {

    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public static void start(int port) throws InterruptedException {

        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap().group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port).childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(echoServerHandler);
            }
        });

        ChannelFuture cf = serverBootstrap.bind().sync();
        System.out.println("启动完成");
        cf.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        start(10086);
    }
}

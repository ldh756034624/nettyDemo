package com.ldh.http_server;

import com.ldh.EchoServerHandler;
import com.ldh.SecondHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by itservice on 2018/1/5.
 */
public class HttpServer {

    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public static void start(int port) throws InterruptedException {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel){
                socketChannel.pipeline().addLast(new HttpServerCodec(),new HttpHandler());
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

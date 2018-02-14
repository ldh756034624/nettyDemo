package com.ldh.message;


import com.ldh.EchoClientHandler;
import com.ldh.OutBoundHandler;
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
public class MessageClient {

    private int port;


     public static void start(int port) throws InterruptedException {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress("127.0.0.1",port)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new WriteHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect().sync();

    }

    public static void main(String[] args) throws InterruptedException {
        start(10086);
    }
}

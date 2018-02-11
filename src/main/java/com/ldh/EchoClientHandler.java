package com.ldh;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * Created by itservice on 2018/1/5.
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        new Thread(() -> {
            while(true){
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes());
                ctx.writeAndFlush(byteBuf);
            }
        }).start();

    }


    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)   {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        String readMsg = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("readMsg: "+readMsg);
    }
}

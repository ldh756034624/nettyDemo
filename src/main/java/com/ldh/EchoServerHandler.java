package com.ldh;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;



/**
 * Created by itservice on 2018/1/5.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));

//        //写回channel
        Channel channel = ctx.channel();
//        ChannelFuture cf = ctx.writeAndFlush(byteBuf);
        ChannelFuture cf = channel.writeAndFlush(byteBuf);

        cf.addListener((ChannelFuture channelFuture) -> {
            if (channelFuture.isSuccess()) {
                System.out.println("回写成功");
            }else{
                System.out.println("回写失败");
            }
        });
    }

}

package com.ldh;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by itservice on 2018/1/5.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private static Map<String, Channel> channelMap = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String name = ctx.name();
        System.out.println("有客户端连接: " + name);
        String id = UUID.randomUUID().toString();
        Channel channel = ctx.channel();
        //分配ID
        channelMap.put(id, channel);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;
        String msgStr = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("接受到的消息："+msgStr);
        sendMsgToChannel(msgStr);

//        ctx.fireChannelRead(byteBuf);
    }

    private void sendMsgToChannel(String msg) {

        channelMap.forEach((id, channel) -> {

            ByteBuf msgByteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
            ChannelFuture cf = channel.writeAndFlush(msgByteBuf);

            cf.addListener((ChannelFuture channelFuture) -> {
                if (channelFuture.isSuccess()) {
                    System.out.println("回写成功,id: "+id);
                } else {
                    System.out.println("回写失败");
                    ChannelFuture closeFuture = channel.closeFuture();
                    if(closeFuture.isSuccess()){
                        System.out.println("关闭成功,id: "+id);
                        channelMap.remove(id);
                    }else{
                        System.out.println("关闭失败,id: "+id);
                    }
                }
            });
        });
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}

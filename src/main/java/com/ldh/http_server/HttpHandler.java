package com.ldh.http_server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static com.sun.deploy.net.HttpRequest.CONTENT_LENGTH;
import static com.sun.deploy.net.HttpRequest.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpHandler extends SimpleChannelInboundHandler<Object>{



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest request = (HttpRequest) msg;
            HttpUtil.printRequestInfo(request);

            String content = getResposeContent();

            ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);
            response.headers().set(CONTENT_TYPE, "text/html;charset=utf-8");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            ChannelFuture closeCF = ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

            if(closeCF.isSuccess()){
                System.out.println("response success");
            }else{
                System.out.println("response fail");
            }
        }
    }

    private String getResposeContent() {
        String content = "<html><body><h1>hello</h1></body></html>";
        return content;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}

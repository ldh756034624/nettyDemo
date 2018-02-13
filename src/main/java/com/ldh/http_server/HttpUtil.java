package com.ldh.http_server;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

public class HttpUtil {
    private HttpUtil(){}

    public static void printRequestInfo(HttpRequest httpRequest) {
        HttpMethod method = httpRequest.getMethod();
        System.out.println("--------------请求信息------------------");
        System.out.println("请求方式:" + method);
        String uri = httpRequest.getUri();
        System.out.println("请求地址:"+uri);
        System.out.println("---------------------------------------");
    }
}

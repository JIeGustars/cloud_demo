package com.Gu.feign.fallbacfactory;


import com.Gu.feign.clients.tokenClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class tokenClientFallbackFactory implements FallbackFactory<tokenClient> {
    @Override
    public tokenClient create(Throwable throwable) {
        return userId -> {
            log.error("生成失败",throwable);
            return "生成失败";
        };
    }
}
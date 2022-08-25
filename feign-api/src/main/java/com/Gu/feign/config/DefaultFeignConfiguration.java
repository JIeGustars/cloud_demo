package com.Gu.feign.config;

import com.Gu.feign.fallbacfactory.tokenClientFallbackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }

    @Bean
    public tokenClientFallbackFactory tokenClientFallbackFactory() {
        return new tokenClientFallbackFactory();
    }

}

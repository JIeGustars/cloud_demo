package com.Gu.feign.fallbacfactory;


import com.Gu.feign.clients.UserClient;
import com.Gu.entity.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public String CreateToken(String userId) {
                log.error("生成失败",throwable);
                return "生成失败";
            }
        };
    }
}
package com.Gu.feign.clients;

import com.Gu.entity.User;
import com.Gu.feign.fallbacfactory.UserClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "tokenserver",fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {


    @GetMapping("/token/create?userId={userId}")
    String CreateToken(@PathVariable("userId") String userId);

}

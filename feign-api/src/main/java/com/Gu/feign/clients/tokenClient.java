package com.Gu.feign.clients;

import com.Gu.feign.fallbacfactory.tokenClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "tokenserver",fallbackFactory = tokenClientFallbackFactory.class)
public interface tokenClient {


    @GetMapping("/token/create?userId={userId}")
    String CreateToken(@PathVariable("userId") String userId);

}

package com.Gu.feign.clients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "tokenserver")
public interface tokenClient {


    @GetMapping("/token/zego?userId={userId}")
    String CreateZeGOToken(@PathVariable("userId") String userId);

    @GetMapping("/token/baidu")
    String CreateBaiDuToken();

}

package com.Gu.feign.clients;

import com.Gu.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "userserver")
public interface userClient {

    @GetMapping("/user/get?id={id}")
    User getUser(@PathVariable("id") int id);
}

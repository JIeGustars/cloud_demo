package com.Gu.es.feign;


import com.Gu.es.entity.MeetingState;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userserver")
public interface userClient {

    @GetMapping("/user/get?Id={Id}")
    MeetingState getUser(@PathVariable("Id") Integer Id);
}

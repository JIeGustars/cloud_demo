package com.Gu.user.feign;


import com.Gu.user.entity.MeetingState;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("EsServer")
public interface esClient {


    @GetMapping("/es/face?meetingId={meetingId}&userPhone={userPhone}")
    String queryFace(@PathVariable("meetingId") String meetingId, @PathVariable("userPhone") String phone);

    @GetMapping("/es/user?meetingId={meetingId}&userPhone={userPhone}")
    MeetingState queryUser(@PathVariable("meetingId") String meetingId, @PathVariable("userPhone") String phone);


}
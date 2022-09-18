package com.Gu.es.controller;

import com.Gu.es.entity.MeetingState;
import com.Gu.es.service.IEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("es")
@Slf4j
@ResponseBody
public class EsController {

    @Resource
    private IEsService iEsService;


    @GetMapping("/face")
    public String queryFace(@RequestParam("meetingId") String meetingId, @RequestParam("userPhone") String userPhone) throws IOException {
        return  iEsService.queryFace(userPhone,meetingId);
    }

    @GetMapping("/user")
    public MeetingState queryUser(@RequestParam("meetingId") String meetingId, @RequestParam("userPhone") String userPhone) throws IOException {
        return  iEsService.queryUser(meetingId,userPhone);

    }



}

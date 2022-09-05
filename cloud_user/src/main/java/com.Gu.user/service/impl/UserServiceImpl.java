package com.Gu.user.service.impl;

import com.Gu.entity.MeetingDate;
import com.Gu.entity.User;
import com.Gu.feign.clients.esClient;
import com.Gu.feign.clients.tokenClient;
import com.Gu.user.mapper.UserMapper;
import com.Gu.user.pojo.MeetingDemo;
import com.Gu.user.pojo.userWeb;
import com.Gu.user.service.UserService;
import com.Gu.user.utils.ArcFaceCompare;
import com.arcsoft.face.FaceEngine;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Resource
    private tokenClient tokenclient;

    @Resource
    private esClient esclient;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ArcFaceCompare arcFace;

    private static final String appId = "3cz2nRe4SJDL11daJijZg25nWCPupUp1BCGe1egtcjzT";
    private static final String sdkKey = "ByukQy2YY8dhnWxuq6uaNcBUdYoFqDcMrm3y6VyyTK8K";
    private static final String activeKey = "86L1-11EG-T13G-FUWA";

    @Override
    public String forget(String userId, String protection) {
        return mapper.forget(userId,protection);
    }

    @Override
    public Boolean Recognition(String picPath1,String picPath2){
        FaceEngine engine = arcFace.initFaceEngine(appId,sdkKey,activeKey);
        String pathName = "E:\\No_work_overtime_meeting\\cloud_demo\\cloud_user\\src\\main\\resources\\images\\";
        picPath1 = pathName+picPath1;
        picPath2 = pathName+picPath2;
        Float result = arcFace.FaceCompare(engine,picPath1,picPath2);
        if (result > 0.65) {
            System.out.println("识别成功");
            return true;
        }
        else {
            System.out.println("识别失败");
            return false;
        }
    }

    @Override
    public String getToken(String userId) {
        return tokenclient.CreateZeGOToken(userId);
    }

    @Override
    public Boolean register(User user) {
        Map<String , Object> map = new HashMap<>();
        map.put("userId",user.getUserId());
        List<User> check = mapper.selectUser(map);
        if (check.isEmpty()){
            mapper.register(user);
            return true;
        }
        else {
            return false;
        }
    }




    @Override
    public User getById(int id) {
        return mapper.getById(id);
    }

    @Override
    public MeetingDemo createMeeting(MeetingDate date) {
        date.setMeetingId(createMeetingId());
        mapper.createMeeting(date);
        MeetingDemo demo = new MeetingDemo();
        demo.setMeetingId(date.getMeetingId());
        demo.setCreateTime(date.getCreateTime());
        demo.setEndTime(date.getEndTime());
        return demo;
    }

    @Override
    public Boolean checkMeetingId(String meetingId) {
        return !mapper.checkMeetingId(meetingId).isEmpty();
    }

    private String createMeetingId() {
        Random rand = new Random();
        StringBuilder meetingId = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            meetingId.append(rand.nextInt(10));
        }
        return meetingId.toString();
    }

    @Override
    public userWeb userLogin(String userId, String password) {
        List<User> result = mapper.userLogin(userId,password);
        if (!result.isEmpty()){
            return new userWeb(true,mapper.getUserName(userId));
        }else {
            return new userWeb(false,null);
        }
    }
}

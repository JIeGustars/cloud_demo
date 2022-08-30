package com.Gu.user.service.impl;

import com.Gu.entity.MeetingDate;
import com.Gu.entity.User;
import com.Gu.feign.clients.esClient;
import com.Gu.feign.clients.tokenClient;
import com.Gu.user.mapper.UserMapper;
import com.Gu.user.pojo.userWeb;
import com.Gu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
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

    @Override
    public String forget(String userId, String protection) {
        return mapper.forget(userId,protection);
    }

    @Override
    public String getToken(String userId) {
        return tokenclient.CreateToken(userId);
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
    public String  createMeeting(MeetingDate date) {
        date.setMeetingId(createMeetingId());
        mapper.createMeeting(date);
        return date.getMeetingId();
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

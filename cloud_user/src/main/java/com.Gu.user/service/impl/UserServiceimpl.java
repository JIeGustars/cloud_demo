package com.Gu.user.service.impl;

import com.Gu.entity.User;

import com.Gu.feign.clients.UserClient;
import com.Gu.user.mapper.UserMapper;
import com.Gu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceimpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Resource
    private UserClient client;

    @Override
    public User getUser(Integer id) {
        User data =  mapper.getUser(id);
        log.debug("消息反馈:"+data);
        return data;
    }

    @Override
    public String getToken(String userId) {
        return client.CreateToken(userId);
    }

    @Override
    public Boolean register(User user) {
        Map<String , Object> map = new HashMap<>();
        map.put("userId",user.getUserId());
        List<User> check = mapper.selectUser(map);
        if (check.isEmpty()){
            return false;
        }
        else {
            mapper.register(user);
            return true;
        }
    }

    @Override
    public Boolean userLogin(String userId, String password) {
        return mapper.userLogin(userId,password);
    }
}


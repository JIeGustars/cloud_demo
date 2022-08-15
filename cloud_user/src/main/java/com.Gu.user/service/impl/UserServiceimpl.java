package com.Gu.user.service.impl;

import com.Gu.entity.User;

import com.Gu.feign.clients.UserClient;
import com.Gu.user.mapper.UserMapper;
import com.Gu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}


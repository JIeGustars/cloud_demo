package com.Gu.user.service.impl;

import com.Gu.entity.User;

import com.Gu.feign.clients.*;
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
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Resource
    private tokenClient tokenclient;

    @Resource
    private esClient esclient;

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
    public String addEs() {
        return esclient.addRequest();
    }

    @Override
    public User getEs() {
        return esclient.GetDocumentById();
    }

    @Override
    public User getById(int id) {
        return mapper.getById(id);
    }

    @Override
    public Boolean userLogin(String userId, String password) {
        List<User> result = mapper.userLogin(userId,password);
        return !result.isEmpty();
    }
}


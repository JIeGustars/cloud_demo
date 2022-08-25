package com.Gu.user.service;

import com.Gu.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    String getToken(String userId);

    Boolean userLogin(String userId, String password);

    Boolean register(User user);

    String forget(String userId, String protection);

    String addEs();

    User getById(int id);

    User getEs();
}

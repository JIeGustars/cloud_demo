package com.Gu.user.service;

import com.Gu.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUser(Integer id);

    String getToken(String userId);
}
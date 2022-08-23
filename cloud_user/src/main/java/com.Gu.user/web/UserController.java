package com.Gu.user.web;

import com.Gu.entity.User;

import com.Gu.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/get")
    public User getUser(@RequestParam Integer id) {
        return userService.getUser(id);
    }

    @GetMapping("/token")
    public String getToken(@RequestParam("userId") String userId) {
        return userService.getToken(userId);
    }

    @GetMapping("/login")
    public Boolean userLogin(@RequestParam("userId") String userId,@RequestParam("password") String password){
        return userService.userLogin(userId,password);
    }
    @PostMapping("/register")
    @ResponseBody
    public Boolean register(User user){
        return userService.register(user);
    }
}

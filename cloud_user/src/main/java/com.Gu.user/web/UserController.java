package com.Gu.user.web;

import com.Gu.entity.User;

import com.Gu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Slf4j
@ResponseBody
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/token")
    public String getToken(@RequestParam("userId") String userId) {
        return userService.getToken(userId);
    }

    @GetMapping("/addEs")
    public String addEs() {
        return userService.addEs();
    }

    @GetMapping("getEs")
    public User getEs() {
        return userService.getEs();
    }

    @GetMapping("/get")
    public User getById(@RequestParam("id") int id) {
        return userService.getById(id);
    }

    @GetMapping("/login")
    public Boolean userLogin(@RequestParam("userId") String userId,@RequestParam("password") String password){
        return userService.userLogin(userId,password);
    }
    @PostMapping("/register")
    public Boolean register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/forget")
    @ResponseBody
    public String forget(@RequestParam("userId") String userId, @RequestParam("protection") String Protection) {
        return userService.forget(userId,Protection);
    }
}

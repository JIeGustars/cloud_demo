package com.Gu.user.web;

import com.Gu.entity.MeetingDate;
import com.Gu.entity.User;
import com.Gu.feign.clients.esClient;
import com.Gu.user.pojo.userWeb;
import com.Gu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Slf4j
@ResponseBody
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private esClient client;


    /**
     * ZeGoRtc创建token所用
     * @param userId 用户id
     * @return 返回token
     */
    @GetMapping("/token")
    public String getToken(@RequestParam("userId") String userId) {
        return userService.getToken(userId);
    }

    /**
     * es测试接口
     * @param id id
     * @return user信息
     */
    @GetMapping("/get")
    public User getById(@RequestParam("id") Integer id) {
        return userService.getById(id);
    }

    /**
     * 用户登录接口
     * @param userId 用户账号
     * @param password 密码
     * @return 返回登录是否成功以及用户的用户名
     */
    @GetMapping("/login")
    public userWeb userLogin(@RequestParam("userId") String userId, @RequestParam("password") String password){
        return userService.userLogin(userId,password);
    }

    /**
     * 账户注册接口
     * @param user 账号信息
     * @return 账号是否注册成功
     */
    @PostMapping("/register")
    public Boolean register(@RequestBody User user){
        return userService.register(user);
    }


    /**
     * 忘记密码接口
     * @param userId 用户账号
     * @param Protection 用户设置的密保
     * @return 返回用户密码
     */
    @GetMapping("/forget")
    @ResponseBody
    public String forget(@RequestParam("userId") String userId, @RequestParam("protection") String Protection) {
        return userService.forget(userId,Protection);
    }

    @PostMapping("/create")
    public String createMeeting(@RequestBody MeetingDate date) {
        return userService.createMeeting(date);
    }
}

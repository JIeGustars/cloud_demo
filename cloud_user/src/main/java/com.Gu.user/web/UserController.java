package com.Gu.user.web;

import com.Gu.entity.MeetingDate;
import com.Gu.entity.User;
import com.Gu.feign.clients.esClient;
import com.Gu.user.pojo.MeetingDemo;
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

    /**
     * 会议创建写入数据库接口
     * @param date 会议创建所需要的类
     * @return 返回会议号，因为其他信息前端皆有，但是会议号由后端随机生成
     */
    @PostMapping("/create")
    public MeetingDemo createMeeting(@RequestBody MeetingDate date) {
        return userService.createMeeting(date);
    }

    @GetMapping("/checkMeetingId")
    public Boolean checkMeetingId(@RequestParam("meetingId") String meetingId) {
        return userService.checkMeetingId(meetingId);
    }

    /**
     * 虹软人脸识别sdk接口
     * @param picPath1 人脸图片1
     * @param picPath2 人脸图片2
     * @return 返回是否识别成功
     */
    @PostMapping("/face")
    public Boolean Recognition(@RequestParam("picPath1") String picPath1,@RequestParam("picPath2") String picPath2) {
        return userService.Recognition(picPath1,picPath2);
    }
}

package com.Gu.user.web;

import com.Gu.user.entity.MeetingDate;
import com.Gu.user.entity.MeetingState;
import com.Gu.user.entity.User;
import com.Gu.user.pojo.MeetingDemo;
import com.Gu.user.pojo.Total;
import com.Gu.user.pojo.faceWeb;
import com.Gu.user.pojo.userWeb;
import com.Gu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@ResponseBody
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private RabbitTemplate rabbitTemplate;


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
     * elastic search会议信息同步
     * @param Id meeting_state表的id
     * @return user信息
     */
    @GetMapping("/get")
    public MeetingState getByName(@RequestParam("Id") Integer Id) {
        return userService.getUser(Id);
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

    /**
     * 会议进入检查接口
     * @param meetingId 会议号：查看是否存在该会议
     * @return 存在true，不存在false
     */
    @GetMapping("/checkMeetingId")
    public userWeb checkMeetingId(@RequestParam("meetingId") String meetingId,@RequestParam("userPhone") String phone) {
        return userService.checkMeetingId(meetingId,phone);
    }

    /**
     * 虹软sdk人脸识别接口
     * @param face 人脸信息，会议号，人名
     * @return 是否成功识别
     */
    @PostMapping("/face")
    public Boolean Recognition(@RequestBody faceWeb face) {
        return userService.Recognition(face);
    }

    /**
     * 会议人员信息录入接口
     * @param user 会议人员信息
     */
    @PostMapping("/add")
    public void addUser(@RequestBody MeetingState user) {
        userService.addUser(user);
    }


    /**
     * 会议签到信息统计
     * @param meetingId 会议号
     * @return 各个状态个数
     */
    @GetMapping("/total")
    public Total total(@RequestParam("meetingId") String meetingId){
        return userService.total(meetingId);
    }


    /**
     * 会议人员信息展示页面的，表单数据展示接口
     * @param pageNum 分页数据
     * @param pageSize 每页个数
     * @param meetingId 会议号
     * @return 返回List<meetingId>和总个数
     */
    @PostMapping("/ShowUserPage")
    public Map<String,Object> ShowUserPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("meetingId") String meetingId){
        Map<String, Object> res=new HashMap<>();
        List<MeetingState> data=userService.showUserPage(pageNum,pageSize,meetingId);
        Integer total=userService.count(meetingId);
        res.put("data",data);
        res.put("total",total);
        return res;
    }

    /**
     * 个人会议信息展示接口
     * 链接MySQL数据库meeting_time表
     * @param pageNum 分页数据
     * @param pageSize 每页个数
     * @param userId 个人账号
     * @return 个人会议信息及总条数
     */
    @PostMapping("/ShowMeetingPage")
    public Map<String,Object> ShowMeetingPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId") String userId){
        Map<String, Object> res=new HashMap<>();
        List<MeetingDate> data=userService.showMeetingPage(pageNum,pageSize,userId);
        res.put("data",data);
        return res;
    }

    /**
     * 进入会议信息录入界面接口
     * 查找meeting_time表
     * @param meetingId 会议号
     * @param userId 申请人姓名
     * @return meeting_time表是否存在与之对应的值
     */
    @GetMapping("/manger")
    public Boolean InManger(@RequestParam("meetingId") String meetingId,@RequestParam("userId") String userId) {
        return userService.InManger(meetingId,userId);
    }

    @GetMapping("/hand")
    public Boolean hand(@RequestBody MeetingState meetingState) {
        return userService.hand(meetingState);
    }
}

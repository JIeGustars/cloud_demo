package com.Gu.user.service.impl;

import com.Gu.user.entity.MeetingDate;
import com.Gu.user.entity.MeetingState;
import com.Gu.user.entity.MqConstants;
import com.Gu.user.entity.User;
import com.Gu.user.feign.esClient;
import com.Gu.user.mapper.UserMapper;
import com.Gu.user.pojo.MeetingDemo;
import com.Gu.user.pojo.Total;
import com.Gu.user.pojo.faceWeb;
import com.Gu.user.pojo.userWeb;
import com.Gu.user.service.UserService;
import com.Gu.user.utils.FaceMatch;
import com.Gu.user.utils.Token;
import com.Gu.user.utils.imgutile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Resource
    private esClient esclient;

    @Resource
    private RabbitTemplate rabbitTemplate;





    @Override
    public String forget(String userId, String protection) {
        return mapper.forget(userId,protection);
    }

    @Override
    public Boolean Recognition(faceWeb face){
        File file = new File("resources/images");
        String path = file.getAbsolutePath().replace("resources\\images","cloud_user\\src\\main\\resources\\images\\");
        String picPath1 = imgutile.base64ToImg(face.getFace(),path).getAbsolutePath();
        String picPath2 = esclient.queryFace(face.getMeetingId(),face.getUserPhone());
        if (picPath2 != null) {
            Double result = FaceMatch.Match(picPath1,picPath2);
            System.out.println("最终结果："+result);
            if (result > 0.65) {
                log.info("识别成功");
                Map<String,String> map = new HashMap<>();
                map.put("userName",face.getUserName());map.put("meetingId",face.getMeetingId());
                mapper.updateState(map);
                rabbitTemplate.convertAndSend(MqConstants.USER_EXCHANGE,MqConstants.USER_INSERT_KEY,face.getUserName());
                return true;
            } else {
                log.error("识别失败");
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public String getToken(String userId) {
        return Token.CreateToken(userId);
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
    public void addUser(MeetingState user) {
        if (user.getFace() != null) {
            File file = new File("resources/face");
            String path = file.getAbsolutePath().replace("resources\\face", "cloud_user\\src\\main\\resources\\face");
            String picPath = imgutile.base64ToImg(user.getFace(), path).getAbsolutePath();
            user.setFace(picPath);
        }
        mapper.addUser(user);
        Integer id = mapper.getUserId(user.getMeetingId(),user.getUserName());
        rabbitTemplate.convertAndSend(MqConstants.USER_EXCHANGE,MqConstants.USER_INSERT_KEY,id.toString());
    }

    @Override
    public Boolean InManger(String meetingId, String userId) {
        return !mapper.InManger(meetingId, userId).isEmpty();
    }

    @Override
    public List<MeetingDate> showMeetingPage(Integer pageNum, Integer pageSize, String userId) {
        pageNum=(pageNum-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        map.put("userId", userId);
        return mapper.ShowMeetingPage(map);
    }

    @Override
    public MeetingState getUser(Integer id) {
        return mapper.getUser(id);
    }

    @Override
    public MeetingDemo createMeeting(MeetingDate date) {
        date.setMeetingId(createMeetingId());
        mapper.createMeeting(date);
        MeetingDemo demo = new MeetingDemo();
        demo.setMeetingId(date.getMeetingId());
        demo.setCreateTime(date.getCreateTime());
        demo.setEndTime(date.getEndTime());
        return demo;
    }

    @Override
    public userWeb checkMeetingId(String meetingId,String phone) {
        MeetingState state = esclient.queryUser(meetingId,phone);
        if (state.getUserName() != null) {
            return new userWeb(true,state.getUserName());
        }
        else {
            return new userWeb(false,null);
        }
    }

    private String createMeetingId() {
        Random rand = new Random();
        StringBuilder meetingId = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            meetingId.append(rand.nextInt(10));
        }
        return meetingId.toString();
    }

    @Override
    public List<MeetingState> showUserPage(Integer pageNum,Integer pageSize,String meetingId) {
        pageNum=(pageNum-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        map.put("meetingId", meetingId);
        return mapper.ShowUserPage(map);
    }

    @Override
    public Integer count(String meetingId) {
        return mapper.count(meetingId);
    }

    @Override
    public Total total(String meetingId) {
        Map<String, Object> map=new HashMap<>();
        map.put("meetingId", meetingId);
        Total total = mapper.total(map);
        System.out.println(total);
        return mapper.total(map);
    }

    @Override
    public userWeb userLogin(String userId, String password) {
        List<User> result = mapper.userLogin(userId,password);
        if (!result.isEmpty()){
            return new userWeb(true,mapper.getUserName(userId));
        }else {
            return new userWeb(false,null);
        }
    }

    @Override
    public Boolean hand(MeetingState meetingState) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName",meetingState.getUserName());
        map.put("userPhone",meetingState.getUserPhone());
        map.put("meetingId",meetingState.getMeetingId());
        if (mapper.handSelect(map).getId() != null){
            mapper.hand(map);
            return true;
        }
        else {
            return false;
        }
    }
}

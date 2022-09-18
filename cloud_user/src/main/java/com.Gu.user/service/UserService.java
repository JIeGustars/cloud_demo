package com.Gu.user.service;


import com.Gu.user.entity.MeetingDate;
import com.Gu.user.entity.MeetingState;
import com.Gu.user.entity.User;
import com.Gu.user.pojo.MeetingDemo;
import com.Gu.user.pojo.Total;
import com.Gu.user.pojo.faceWeb;
import com.Gu.user.pojo.userWeb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    String getToken(String userId);

    userWeb userLogin(String userId, String password);

    Boolean register(User user);

    String forget(String userId, String protection);


    MeetingState getUser(Integer Id);

    MeetingDemo createMeeting(MeetingDate date);

    userWeb checkMeetingId(String meetingId,String phone);

    Boolean Recognition(faceWeb face);

    void addUser(MeetingState user);

    Total total(String meetingId);

    List<MeetingState> showUserPage(Integer pageNum,Integer pageSize,String meetingId);

    Integer count(String meetingId);

    Boolean InManger(String meetingId, String userId);

    List<MeetingDate> showMeetingPage(Integer pageNum, Integer pageSize, String userId);


    Boolean hand(MeetingState meetingState);
}

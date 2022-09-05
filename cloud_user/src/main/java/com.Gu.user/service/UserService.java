package com.Gu.user.service;

import com.Gu.entity.MeetingDate;
import com.Gu.entity.User;
import com.Gu.user.pojo.MeetingDemo;
import com.Gu.user.pojo.userWeb;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    String getToken(String userId);

    userWeb userLogin(String userId, String password);

    Boolean register(User user);

    String forget(String userId, String protection);


    User getById(int id);

    MeetingDemo createMeeting(MeetingDate date);

    Boolean checkMeetingId(String meetingId);

    Boolean Recognition(String picPath1,String picPath2);
}

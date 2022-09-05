package com.Gu.user.mapper;

import com.Gu.entity.MeetingDate;
import com.Gu.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    List<User> userLogin(String userId, String password);

    List<User> selectUser(Map<String, Object> map);

    void register(User user);

    String forget(String userId, String protection);

    User getById(int id);


    String getUserName(String userId);

    void createMeeting(MeetingDate date);

    List<MeetingDate> checkMeetingId(String meetingId);
}

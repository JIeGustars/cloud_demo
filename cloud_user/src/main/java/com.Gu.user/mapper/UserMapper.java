package com.Gu.user.mapper;


import com.Gu.user.entity.MeetingDate;
import com.Gu.user.entity.MeetingState;
import com.Gu.user.entity.User;
import com.Gu.user.pojo.Total;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    List<User> userLogin(String userId, String password);

    List<User> selectUser(Map<String, Object> map);

    void register(User user);

    String forget(String userId, String protection);

    MeetingState getUser(Integer Id);


    String getUserName(String userId);

    void createMeeting(MeetingDate date);

    MeetingState checkMeetingId(Map<String , Object> map);

    void addUser(MeetingState user);

    Total total(Map<String, Object> map);

    Integer count(String meetingId);

    List<MeetingState> ShowUserPage(Map<String, Object> map);

    void updateState(Map<String, String> map);

    Integer getUserId(String meetingId, String userName);

    List<MeetingDate> InManger(String meetingId, String userId);

    List<MeetingDate> ShowMeetingPage(Map<String, Object> map);

    MeetingState handSelect(Map<String, Object> map);

    void hand(Map<String, Object> map);
}

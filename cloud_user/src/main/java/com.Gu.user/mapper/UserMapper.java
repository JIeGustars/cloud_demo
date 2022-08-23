package com.Gu.user.mapper;

import com.Gu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

//    @Select("select * from tb_order where id = #{Id}")
    User getUser(Integer id);

    Boolean userLogin(String userId, String password);

    List<User> selectUser(Map<String, Object> map);

    void register(User user);
}

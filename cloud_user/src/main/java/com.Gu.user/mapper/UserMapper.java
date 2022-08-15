package com.Gu.user.mapper;

import com.Gu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

//    @Select("select * from tb_order where id = #{Id}")
    User getUser(Integer id);
}

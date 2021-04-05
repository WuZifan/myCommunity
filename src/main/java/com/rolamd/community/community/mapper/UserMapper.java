package com.rolamd.community.community.mapper;


import com.rolamd.community.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user " +
            "(account_id,name,token,gmt_create,gmt_modified) " +
            "values " +
            "(#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);


    @Select("SELECT * from user where token=#{token}")
    User getUserByToken(@Param("token") String token);

    @Select("SELECT * FROM user where account_id=#{accountId}")
    User getUserByAcccountId(@Param("accountId") String accountId);

    @Update("UPDATE user " +
            "set token=#{token},gmt_modified=#{gmtModified}" +
            "where account_id=#{accountId}")
    int updateUserTokenByAccountId(User user);
}

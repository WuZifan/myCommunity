package com.rolamd.community.community.mapper;


import com.rolamd.community.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user " +
            "(account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) " +
            "values " +
            "(#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);


    @Select("SELECT * from user where token=#{token} LIMIT 1")
    User getUserByToken(@Param("token") String token);

    @Select("SELECT * FROM user where account_id=#{accountId} LIMIT 1")
    User getUserByAcccountId(@Param("accountId") String accountId);

//    @Results({
//            @Result(property = "id",column = "id",id = true),
//            @Result(property = "gmtCreate",column = "gmt_create"),
//            @Result(property = "gmtModified",column = "gmt_modified"),
//            @Result(property = "avatarUrl",column = "avatar_url")
//    })
    @Select("SELECT * FROM user where id=#{id}")
    User selectById(@Param("id") int id);

    @Update("UPDATE user " +
            "set name=#{name},token=#{token},gmt_modified=#{gmtModified}," +
            "bio=#{bio},avatar_url=#{avatarUrl} " +
            "where account_id=#{accountId}")
    int updateUserTokenByAccountId(User user);
}

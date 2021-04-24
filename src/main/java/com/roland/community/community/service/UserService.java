package com.roland.community.community.service;

import com.roland.community.community.mapper.UserMapper;
import com.roland.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user){
        User oldUser = userMapper.getUserByAcccountId(user.getAccountId());
        if(oldUser == null){
            userMapper.insert(user);
        }else{
            userMapper.updateUserTokenByAccountId(user);
        }
    }
}

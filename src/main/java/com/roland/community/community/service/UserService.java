package com.roland.community.community.service;

import com.roland.community.community.mapper.UserMapper;
import com.roland.community.community.model.User;
import com.roland.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
//        User oldUser = userMapper.getUserByAcccountId(user.getAccountId());
        if(users.size() == 0){
            userMapper.insert(user);
        }else{

            User userUpdate = new User();
            userUpdate.setGmtModified(System.currentTimeMillis());
            userUpdate.setAvatarUrl(user.getAvatarUrl());
            userUpdate.setBio(user.getBio());
            userUpdate.setName(user.getName());
            userUpdate.setToken(user.getToken());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(userUpdate,userExample1);
        }
    }
}

package com.roland.community.community.mapper;

import com.roland.community.community.model.Notification;
import com.roland.community.community.model.Question;

import java.util.List;
import java.util.Map;

public interface NotificationExtMapper {

    List<Notification> selectPage(Map map);

    List<Notification> selectPageByUser(Map map);

}

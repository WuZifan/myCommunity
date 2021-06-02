package com.roland.community.community.service;

import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.Exception.CustomizeException;
import com.roland.community.community.dto.NotificationDTO;
import com.roland.community.community.dto.PaginationDTO;
import com.roland.community.community.enums.NotificationStatusEnum;
import com.roland.community.community.enums.NotificationTypeEnum;
import com.roland.community.community.mapper.*;
import com.roland.community.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationExtMapper notificationExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    public PaginationDTO listAllNotification(Long userId,int page,int size){

        // 总页数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);

        Integer totalCnt = (int)notificationMapper.countByExample(notificationExample);

        if(page> totalCnt/size){
            page =totalCnt/size;
        }else if(page<1){
            page=1;
        }

        // 计算当前页起始位置
        Integer offset = size*(page-1);



        // 拿到当前页问题
        Map<String,Object> map = new HashMap<>();
        map.put("creator",userId);
        map.put("offset",offset);
        map.put("limit",size);

        List<Notification> notifications = notificationExtMapper.selectPageByUser(map);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

//        User user = userMapper.selectByPrimaryKey(userId);
        for (Notification notification: notifications
                ) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);

            notificationDTO.setNotifier(userMapper.selectByPrimaryKey(notification.getNotifier()));
            if(notification.getType().equals(NotificationTypeEnum.REPLY_QUESTION.getType())) {
                notificationDTO.setOuterTitle(questionMapper.selectByPrimaryKey(notification.getOuterid()).getTitle());
            }else{
                notificationDTO.setOuterTitle(commentMapper.selectByPrimaryKey(notification.getOuterid()).getContent());
            }
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setPageDTOList(notificationDTOS);
        paginationDTO.setPagination(totalCnt,page,size);

        return paginationDTO;
    }

    public Long getUnreadCnt(Long id) {
        // 总页数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(0);

        Long totalCnt = notificationMapper.countByExample(notificationExample);
        return totalCnt;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAILED);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setNotifier(userMapper.selectByPrimaryKey(notification.getNotifier()));
        if(notification.getType().equals(NotificationTypeEnum.REPLY_QUESTION.getType())) {
            notificationDTO.setOuterTitle(questionMapper.selectByPrimaryKey(notification.getOuterid()).getTitle());
        }else{
            notificationDTO.setOuterTitle(commentMapper.selectByPrimaryKey(notification.getOuterid()).getContent());
        }
        return notificationDTO;
    }
}

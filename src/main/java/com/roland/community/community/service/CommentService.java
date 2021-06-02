package com.roland.community.community.service;

import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.Exception.CustomizeException;
import com.roland.community.community.dto.CommentDTO;
import com.roland.community.community.enums.CommentTypeEnum;
import com.roland.community.community.enums.NotificationStatusEnum;
import com.roland.community.community.enums.NotificationTypeEnum;
import com.roland.community.community.mapper.*;
import com.roland.community.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null || comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARENT_NOT_FOUND);
        }

        if(comment.getType()==null || (!CommentTypeEnum.isExist(comment.getType()))){
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_FOUND);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
//            Question parentQues = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            commentExtMapper.increseComment(dbComment);

           // 创建通知
            this.createNotify(comment,dbComment.getCommentator(),NotificationTypeEnum.REPLY_COMMENT);

        }else{
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionExtMapper.increseComment(question);

            // 创建通知
            this.createNotify(comment,question.getCreator(),NotificationTypeEnum.REPLY_QUESTION);
        }
    }

    private void createNotify(Comment comment,Long receiver,NotificationTypeEnum notificationTypeEnum){
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        notification.setOuterid(comment.getParentId());
        notification.setType(notificationTypeEnum.getType());
        notification.setReceiver(receiver);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);

    }



    public List<CommentDTO> listByQuestionId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for(Comment cmt : comments){
//            System.out.println(cmt);
            CommentDTO tempDTO = new CommentDTO();
            BeanUtils.copyProperties(cmt,tempDTO);
//            System.out.println(tempDTO);
            User tmpUser = userMapper.selectByPrimaryKey(cmt.getCommentator());
            tempDTO.setUser(tmpUser);
            commentDTOS.add(tempDTO);
        }

        return commentDTOS;
    }
}

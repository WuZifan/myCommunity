package com.roland.community.community.service;

import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.Exception.CustomizeException;
import com.roland.community.community.dto.CommentDTO;
import com.roland.community.community.enums.CommentTypeEnums;
import com.roland.community.community.mapper.*;
import com.roland.community.community.model.Comment;
import com.roland.community.community.model.CommentExample;
import com.roland.community.community.model.Question;
import com.roland.community.community.model.User;
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

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null || comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARENT_NOT_FOUND);
        }

        if(comment.getType()==null || (!CommentTypeEnums.isExist(comment.getType()))){
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_FOUND);
        }

        if(comment.getType() == CommentTypeEnums.COMMENT.getType()){
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
//            Question parentQues = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            commentExtMapper.increseComment(dbComment);
        }else{
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionExtMapper.increseComment(question);
        }
    }

    public List<CommentDTO> listByQuestionId(Long id, CommentTypeEnums type) {
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

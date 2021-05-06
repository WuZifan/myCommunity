package com.roland.community.community.service;

import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.Exception.CustomizeException;
import com.roland.community.community.enums.CommentTypeEnums;
import com.roland.community.community.mapper.CommentMapper;
import com.roland.community.community.mapper.QuestionExtMapper;
import com.roland.community.community.mapper.QuestionMapper;
import com.roland.community.community.model.Comment;
import com.roland.community.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;


    @Autowired
    private QuestionExtMapper questionExtMapper;

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
            Question parentQues = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            questionExtMapper.increseComment(parentQues);
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
}

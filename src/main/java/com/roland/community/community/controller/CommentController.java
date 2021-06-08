package com.roland.community.community.controller;

import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.dto.CommentCreateDTO;
import com.roland.community.community.dto.CommentDTO;
import com.roland.community.community.dto.ResponseResultDTO;
import com.roland.community.community.enums.CommentTypeEnum;
import com.roland.community.community.mapper.CommentMapper;
import com.roland.community.community.model.Comment;
import com.roland.community.community.model.User;
import com.roland.community.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return ResponseResultDTO.errorOf(CustomizeErrorCode.LOGIN_ERROR);
        }

        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResponseResultDTO.errorOf(CustomizeErrorCode.CONETNT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);

//        commentMapper.selectByPrimaryKey(commentCreateDTO.getParentId());

        commentService.insert(comment);

        return ResponseResultDTO.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResponseResultDTO<List> comments(@PathVariable(name = "id") Long id){

        List<CommentDTO> comments = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT);

        return ResponseResultDTO.okOf(comments);
    }
}

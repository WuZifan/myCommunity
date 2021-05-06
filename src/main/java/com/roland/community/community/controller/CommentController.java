package com.roland.community.community.controller;

import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.dto.CommentDTO;
import com.roland.community.community.dto.ResponseResultDTO;
import com.roland.community.community.mapper.CommentMapper;
import com.roland.community.community.model.Comment;
import com.roland.community.community.model.User;
import com.roland.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
//        if(user==null){
//            return ResponseResultDTO.errorOf(CustomizeErrorCode.LOGIN_ERROR);
//        }

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getTypeId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1);

        commentService.insert(comment);

        return ResponseResultDTO.ok();
    }
}

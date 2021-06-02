package com.roland.community.community.controller;

import com.roland.community.community.dto.PaginationDTO;
import com.roland.community.community.model.Notification;
import com.roland.community.community.model.User;
import com.roland.community.community.service.NotificationService;
import com.roland.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String getMyQuestion(
            Model model,
            HttpServletRequest request,
            @PathVariable(name = "action",value = "")String action,
            @RequestParam(name = "page",defaultValue = "1")Integer page,
            @RequestParam(name = "size",defaultValue = "5")Integer size){

        User user = (User)request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }


//        Long unreadCnt = notificationService.getUnreadCnt(user.getId());

        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.getQuestionByUserId(user.getId(),page,size);
            model.addAttribute("PaginationDTO",paginationDTO);


        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            PaginationDTO paginationReplyDTO = notificationService.listAllNotification(user.getId(),page,size);
            model.addAttribute("PaginationDTO",paginationReplyDTO);
        }

//        model.addAttribute("unreadCnt",unreadCnt);

        return "profile";
    }
}

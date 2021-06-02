package com.roland.community.community.controller;

import com.roland.community.community.dto.NotificationDTO;
import com.roland.community.community.dto.PaginationDTO;
import com.roland.community.community.enums.NotificationTypeEnum;
import com.roland.community.community.mapper.NotificationMapper;
import com.roland.community.community.model.User;
import com.roland.community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationMapper notificationMapper;

    @GetMapping("/notification/{id}")
    public String getMyQuestion(HttpServletRequest request,
                                @PathVariable(name = "id") Long id){

        User user = (User)request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }



        NotificationDTO notificationDTO =  notificationService.read(id,user);
//        Long unreadCnt = notificationService.getUnreadCnt(user.getId());
        if(notificationDTO.getType().equals(NotificationTypeEnum.REPLY_QUESTION.getType())){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }
    }
}

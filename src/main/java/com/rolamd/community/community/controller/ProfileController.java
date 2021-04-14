package com.rolamd.community.community.controller;

import com.rolamd.community.community.dto.PaginationDTO;
import com.rolamd.community.community.mapper.UserMapper;
import com.rolamd.community.community.model.User;
import com.rolamd.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String getMyQuestion(
            Model model,
            HttpServletRequest request,
            @PathVariable(name = "action",value = "")String action,
            @RequestParam(name = "page",defaultValue = "1")Integer page,
            @RequestParam(name = "size",defaultValue = "5")Integer size){

        User user = null;
        if(request.getCookies()!=null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    System.out.println("token: "+token);
                    user = userMapper.getUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user == null){
            return "redirect:/";
        }

        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");


        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");

//            model.addAttribute("PaginationDTO",null);

        }
        PaginationDTO paginationDTO = questionService.getQuestionByUserId(user.getId(),page,size);

        model.addAttribute("PaginationDTO",paginationDTO);

        return "profile";
    }
}

package com.rolamd.community.community.controller;

import com.rolamd.community.community.dto.PaginationDTO;
import com.rolamd.community.community.dto.QuestionDTO;
import com.rolamd.community.community.mapper.QuestionMapper;
import com.rolamd.community.community.mapper.UserMapper;
import com.rolamd.community.community.model.Question;
import com.rolamd.community.community.model.User;
import com.rolamd.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GreetingController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(name = "name",defaultValue = "roland",required = false)String name, Model model){
        model.addAttribute("name",name);
        return "greeting";
    }

    @RequestMapping({"/","/index"})
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")String page,
                        @RequestParam(name = "size",defaultValue = "5")String size){
        System.out.println(request.getSession().getAttribute("user"));
        if(request.getCookies()!=null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    System.out.println("token: "+token);
                    User user = userMapper.getUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

//        List<QuestionDTO> questions = questionService.selectAll();

        PaginationDTO paginationDTO = questionService.selectPage(Integer.valueOf(page),Integer.valueOf(size));

        model.addAttribute("PaginationDTO",paginationDTO);
//        for(QuestionDTO questionDTO:questions){
//            System.out.println(questionDTO.getTitle()+" "+questionDTO.getDescription()+" "+questionDTO.toString());
//        }
        return "index";
    }
}

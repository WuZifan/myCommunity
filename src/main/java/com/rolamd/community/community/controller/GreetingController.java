package com.rolamd.community.community.controller;

import com.rolamd.community.community.mapper.UserMapper;
import com.rolamd.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GreetingController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(name = "name",defaultValue = "roland",required = false)String name, Model model){
        model.addAttribute("name",name);
        return "greeting";
    }

    @RequestMapping({"/","/index"})
    public String index(HttpServletRequest request){
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
        System.out.println(request.getSession().getAttribute("user"));
        System.out.println("hello");
        return "index";
    }
}

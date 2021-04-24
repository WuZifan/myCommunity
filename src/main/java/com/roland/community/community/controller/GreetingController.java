package com.roland.community.community.controller;

import com.roland.community.community.dto.PaginationDTO;

import com.roland.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GreetingController {


    @Autowired
    private QuestionService questionService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(name = "name",defaultValue = "roland",required = false)String name, Model model){
        model.addAttribute("name",name);
        return "greeting";
    }

    @RequestMapping({"/","/index","/index2"})
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size){


        PaginationDTO paginationDTO = questionService.selectPage(page,size);

        model.addAttribute("PaginationDTO",paginationDTO);


        return "index";
    }
}

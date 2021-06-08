package com.roland.community.community.controller;

import com.roland.community.community.dto.PaginationDTO;

import com.roland.community.community.dto.QuestionDTO;
import com.roland.community.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = {"/","/index","/index2"})
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size,
                        @RequestParam(name = "search",required =false) String search){

//        System.out.println("search is "+search);
//        System.out.println("page is "+ page);
//        System.out.println("size is "+size);

        PaginationDTO paginationDTO = questionService.selectPage(search,page,size);

        model.addAttribute("PaginationDTO",paginationDTO);
        model.addAttribute("search", search);


        return "index";
    }
}

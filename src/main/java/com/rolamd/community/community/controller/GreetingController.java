package com.rolamd.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(name = "name",defaultValue = "roland",required = false)String name, Model model){
        model.addAttribute("name",name);
        return "greeting";
    }

    @RequestMapping("/")
    public String index(){
        System.out.println("hello");
        return "index";
    }
}

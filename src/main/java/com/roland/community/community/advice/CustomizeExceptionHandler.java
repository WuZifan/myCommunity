package com.roland.community.community.advice;


import com.alibaba.fastjson.JSON;
import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.Exception.CustomizeException;
import com.roland.community.community.dto.ResponseResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    String handleControllerException(HttpServletRequest request, Throwable ex, Model model) {
//        HttpStatus status = getStatus(request);
//        model.addAttribute("errorMessage",ex.getMessage());
//        return "error";
//    }

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Throwable ex,
                                           Model model) {

        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResponseResultDTO responseResultDTO=null;
            if (ex instanceof CustomizeException){
                responseResultDTO =  ResponseResultDTO.errorOf((CustomizeException)ex);
            }else{
                responseResultDTO =  ResponseResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERRPR);
            }


            PrintWriter writer = null;
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf8");
                writer = response.getWriter();
                writer.write(JSON.toJSONString(responseResultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }else {
            model.addAttribute("errorMessage", ex.getMessage());
            return new ModelAndView("error");
        }
    }


}

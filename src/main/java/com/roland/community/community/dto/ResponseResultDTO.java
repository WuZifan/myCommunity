package com.roland.community.community.dto;

import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.Exception.CustomizeException;
import lombok.Data;

@Data
public class ResponseResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResponseResultDTO errorOf(Integer code,String message){

        ResponseResultDTO responseResultDTO = new ResponseResultDTO();
        responseResultDTO.setCode(code);
        responseResultDTO.setMessage(message);

        return responseResultDTO;
    }

    public static ResponseResultDTO errorOf(CustomizeErrorCode loginError) {
        return errorOf(loginError.getCode(),loginError.getMessage());
    }

    public static ResponseResultDTO ok(){

        ResponseResultDTO responseResultDTO = new ResponseResultDTO();
        responseResultDTO.setCode(200);
        responseResultDTO.setMessage("success");
        return responseResultDTO;
    }

    public static ResponseResultDTO errorOf(CustomizeException ex) {

        return errorOf(ex.getCode(),ex.getMessage());
    }

    public static <T> ResponseResultDTO okOf(T t) {
        ResponseResultDTO responseResultDTO = new ResponseResultDTO();
        responseResultDTO.setCode(200);
        responseResultDTO.setMessage("success");
        responseResultDTO.setData(t);
        return responseResultDTO;
    }
}

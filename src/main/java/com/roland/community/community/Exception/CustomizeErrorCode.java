package com.roland.community.community.Exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"Question Not Found"),
    TARGET_PARENT_NOT_FOUND(2002,"未选中问题或评论进行回复"),
    LOGIN_ERROR(2003,"未登录"),
    SYSTEM_ERRPR(2004,"服务器异常"),
    TYPE_NOT_FOUND(2005,"类型不存在" ),
    COMMENT_NOT_FOUND(2006,"操作的评论不存在" ),
    CONETNT_IS_EMPTY(2007,"输入评论为空");

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    private String message;
    private Integer code;
    CustomizeErrorCode(Integer code,String message) {
        this.code=code;
        this.message=message;
    }
}

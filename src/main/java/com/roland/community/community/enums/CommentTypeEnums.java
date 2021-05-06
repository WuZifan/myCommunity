package com.roland.community.community.enums;

public enum CommentTypeEnums {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnums(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnums com:CommentTypeEnums.values()) {
            if(com.getType() == type){
                return true;
            }
        }
        return false;
    }
}

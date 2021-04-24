package com.roland.community.community.dto;

import com.roland.community.community.model.Question;
import com.roland.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO extends Question{
    private User user;

}

package com.rolamd.community.community.dto;

import com.rolamd.community.community.model.Question;
import com.rolamd.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO extends Question{
    private User user;

}

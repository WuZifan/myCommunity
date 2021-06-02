package com.roland.community.community.dto;

import com.roland.community.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Integer status;
    private Long gmtCreate;
    private User notifier;
    private String outerTitle;
    private Integer type;
    private Long outerid;
}

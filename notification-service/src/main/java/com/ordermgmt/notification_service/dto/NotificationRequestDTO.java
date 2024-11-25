package com.ordermgmt.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDTO {
    private Long userId;
    private String message;
}

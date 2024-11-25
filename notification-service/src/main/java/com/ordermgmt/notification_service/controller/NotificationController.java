package com.ordermgmt.notification_service.controller;

import com.ordermgmt.notification_service.dto.NotificationRequestDTO;
import com.ordermgmt.notification_service.model.Notification;
import com.ordermgmt.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody NotificationRequestDTO requestDTO) {
        notificationService.createNotification(requestDTO.getUserId(), requestDTO.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body("Notification created and sent.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }
}

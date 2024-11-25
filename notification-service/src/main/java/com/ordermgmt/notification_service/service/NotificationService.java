package com.ordermgmt.notification_service.service;

import com.ordermgmt.notification_service.dto.OrderStatusDTO;
import com.ordermgmt.notification_service.model.Notification;
import com.ordermgmt.notification_service.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @RabbitListener(queues = "order_status_queue")
    public void handleOrderStatusUpdate(OrderStatusDTO orderStatusDTO) {
        String message = "Your order with ID " + orderStatusDTO.getOrderId() +
                " is now " + orderStatusDTO.getStatus();
        createNotification(orderStatusDTO.getUserId(), message);
    }

    public void createNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setStatus("PENDING");
        notification.setCreatedAt(LocalDateTime.now());

        // Save notification
        notificationRepository.save(notification);

        // Simulate sending the notification
        sendNotification(notification);
    }

    private void sendNotification(Notification notification) {
        try {
            // Simulate email/SMS sending (replace with real logic)
            System.out.println("Sending notification to user " + notification.getUserId() + ": " + notification.getMessage());
            notification.setStatus("SENT");
        } catch (Exception e) {
            notification.setStatus("FAILED");
        } finally {
            notificationRepository.save(notification);
        }
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}

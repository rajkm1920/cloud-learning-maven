package com.cloud.azure.notificationhub.controller;

import com.cloud.azure.notificationhub.service.SendMessageToNH;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationHubController {

    private final SendMessageToNH notificationService;

    public NotificationHubController(SendMessageToNH notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("/sendNotification")
    public String sendNotification() {
        String message ="message for notification Hub";
        notificationService.sendNotificationWithSDK(message);
        return "Sending Notification.....";
    }

}

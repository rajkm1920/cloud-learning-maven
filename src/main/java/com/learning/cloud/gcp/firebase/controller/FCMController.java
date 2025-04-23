package com.learning.cloud.gcp.firebase.controller;

import com.learning.cloud.gcp.firebase.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FCMController {

    @Autowired
    FCMService fcmService;
    int messageCount ;
    @Value("${firebase.notification.device-token}")
    public String device_token;


    @Scheduled(fixedRate = 5000 )
    public void sendPushNotification(){
        String message = "Test message for Notification";
        fcmService.sendNotificationToDevice(device_token,message);
        System.err.println("Sending...");
    }




}

package com.cloud.gcp.firebase.controller;

import com.cloud.gcp.firebase.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class FCMController {

    @Autowired
    FCMService fcmService;
    int messageCount ;
    @Value("${firebase.notification.device-token}")
    public String device_token;


    @Scheduled(fixedRate = 5000 )
    public void sendPushNotification(){

        String message = "";
        LocalDateTime startTime = LocalDateTime.now();
        List<String> list = Stream.generate(String::new).limit(1000).collect(Collectors.toList());
        System.err.println("List Size: "+list.size());
        Duration d = Duration.between(startTime,LocalDateTime.now());

        for(int i=0 ; i<5; i++){
            startTime = LocalDateTime.now();
            fcmService.sendNotificationToDevice(device_token,message+i);
            d = Duration.between(startTime,LocalDateTime.now());
            System.err.println("Single Push Notification taken Time:"+  d.getNano());
        }
        d = Duration.between(startTime, LocalDateTime.now());
        System.err.println("Total time taken for 1000 :"+  d.getSeconds());

//        fcmService.sendNotificationToDevice(token,""+messageCount++);
    }




}

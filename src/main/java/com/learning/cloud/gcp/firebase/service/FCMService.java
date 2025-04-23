package com.learning.cloud.gcp.firebase.service;

import com.google.firebase.messaging.*;
import com.windowsazure.messaging.FcmV1Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FCMService {

    @Autowired
    FirebaseMessaging firebaseMessaging;

    public void sendNotificationToDevice(String token, String message){
        try {

            LocalDateTime timeStamp = LocalDateTime.now();

            Map<String, String> data = new HashMap<>();
                    data.put("Title", "This is push data");
                    data.put("Consignment", "880967282769");
                    data.put("Message", "Message delivered "+message);
                    data.put("DeviceId", "IT~CT1~~MC67NA~~~~CT1056");

            AndroidConfig  androidConfig = AndroidConfig.builder()
                    .setCollapseKey("Load Scan")
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setTtl(120)
                    .setNotification(AndroidNotification.builder()
                            .setTitle("Notification Body: This my push notification title")
                            .setBody("Notification Body: Message delivered"+message)
                            .setChannelId("FedexDefaultChannel")
                            .setTag("DeliveryStatusTag")
                            .build())
                    .build();

            ApnsConfig apnsConfig = ApnsConfig.builder()
                    .setAps(Aps.builder()
                            .setCategory("APS:Category Load Scan")
                            .setThreadId("APS:ThreadId Load Scan")
                            .build())
                    .build();

            Message messagetoDevice = Message.builder()
                    .putAllData(data)
                    .setAndroidConfig(androidConfig)
                    .setApnsConfig(apnsConfig)
                    .setToken(token).build();
            String id =firebaseMessaging.send(messagetoDevice);



            /** Batch Processing **/
           /* BatchResponse batchResponse = firebaseMessaging.sendEach(batchMessageBuilder(token,message,data));
            batchResponse.getFailureCount();
            batchResponse.getSuccessCount();
            for(SendResponse res : batchResponse.getResponses()){
                System.err.println(res.getException());
                System.err.println(res.getMessageId());
                System.err.println(res.isSuccessful());
            }*/

            System.err.println("Tracking Id: "+id+"\tSent at: "+ timeStamp.toString()+"\tMessageCount :"+message);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Message messageBuilder(String token, String message, Map<String, String> data){

        AndroidConfig  androidConfig = AndroidConfig.builder()
                .setCollapseKey("Load Scan")
                .setPriority(AndroidConfig.Priority.HIGH)
                .setTtl(120)
                .setNotification(AndroidNotification.builder()
                        .setTitle("Notification Body: This my push notification title")
                        .setBody("Notification Body: Message delivered"+message)
                        .setChannelId("FedexDefaultChannel")
                        .setTag("DeliveryStatusTag")
                        .build())
                .build();

        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory("APS:Category Load Scan")
                        .setThreadId("APS:ThreadId Load Scan")
                        .build())
                .build();

        return Message.builder()
                .setAndroidConfig(androidConfig)
                .setApnsConfig(apnsConfig)
                .build();
    }

    /*<!--- 500 Messages we can send in a batch -->*/
    public List<Message> batchMessageBuilder(String token, String message, Map<String, String> data){


        AndroidConfig  androidConfig = AndroidConfig.builder()
                .setCollapseKey("Load Scan")
                .setPriority(AndroidConfig.Priority.HIGH)
                .setTtl(120)
                .setNotification(AndroidNotification.builder()
                        .setTitle("Notification Body: This my push notification title")
                        .setBody("Notification Body: Message delivered"+message)
                        .setChannelId("FedexDefaultChannel")
                        .setTag("DeliveryStatusTag")
                        .build())
                .build();

        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory("APS:Category Load Scan")
                        .setThreadId("APS:ThreadId Load Scan")
                        .build())
                .build();

        return List.of(Message.builder()
                .setAndroidConfig(androidConfig)
                .setApnsConfig(apnsConfig)
                .build(),
                Message.builder()
                        .setAndroidConfig(androidConfig)
                        .setApnsConfig(apnsConfig)
                        .build(),
                Message.builder()
                        .setAndroidConfig(androidConfig)
                        .setApnsConfig(apnsConfig)
                        .build(),
                Message.builder()
                        .setAndroidConfig(androidConfig)
                        .setApnsConfig(apnsConfig)
                        .build());
    }




}

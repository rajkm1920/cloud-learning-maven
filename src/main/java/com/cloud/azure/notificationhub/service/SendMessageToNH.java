package com.cloud.azure.notificationhub.service;


import com.cloud.azure.eventhub.service.EventHubReceiverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.windowsazure.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SendMessageToNH {

    @Value("${azure.notificationhub.restendpoint}")
    public String NH_REST_ENDPOINT;
    @Value("${azure.notificationhub.connectionstring}")
    public String NH_CONNECTION_STRING_SDK;
    @Value("${azure.notificationhub.name}")
    public String NH_NAME ;
    @Value("${azure.notificationhub.apiversion}")
    public String NH_API_VERSION_MAP;


    @Autowired
    EventHubReceiverService receiverService;

    int regCount =0 ;
    public void sendNotificationWithSDK(String message){

        try {
            //NotificationHubClient notificationHubClient = new NotificationHub(NH_CONNECTION_STRING_SDK, NH_NAME);

            NotificationHub notificationHub = installationFcmv1Notification();

            if(regCount==0) {
                //System.err.println("Create Registration:\t" + createRegistration(notificationHubClient));
                notificationHub = installationFcmv1Notification();
                System.err.println("fcmv1 android-installation-id: ");
                regCount++;
            }
            NotificationOutcome outcome = sendFcmV1Notification(notificationHub);
            System.err.println("GetTrackingId:\t "+outcome.getTrackingId());
//            notification.setBody(messageBody(message));
//            NotificationOutcome outcome = notificationHubClient.sendNotification(notification);
//            System.err.println("getTrackingId:\t "+outcome.getTrackingId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public NotificationOutcome sendFcmV1Notification( NotificationHub notificationHub) throws NotificationHubsException {
            NotificationOutcome outcome = notificationHub.sendNotification(new FcmV1Notification("{\"message\":{\"android\":{\"data\":{\"message\":\"Notification Hub test Fcmv1 notification\"}}}}"));
            return outcome;
    }
    public NotificationOutcome sendFcmNotification( NotificationHub notificationHub) throws NotificationHubsException {
        NotificationOutcome outcome = notificationHub.sendNotification(new FcmNotification("{\"message\":{\"android\":{\"data\":{\"message\":\"Notification Hub test Fcmv1 notification\"}}}}"));
        return outcome;
    }

//    @Scheduled(fixedRate = 5000 )
    public String fetchMessage(){
        System.err.println("\n\nDateTime:\t"+ LocalDateTime.now());
        // String eventData = receiverService.receiveMessages();
        String eventData = "Event Data for notification Hub";
        sendNotificationWithSDK(eventData);
        return eventData;
    }


    public NotificationHub installationFcmv1Notification(){
        NotificationHub notificationHub = null;
        try {
            notificationHub = new NotificationHub(NH_CONNECTION_STRING_SDK, NH_NAME);
            notificationHub.createOrUpdateInstallation(new Installation("ins-id-"+UUID.randomUUID(), NotificationPlatform.FcmV1, "device-token"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return notificationHub;
    }


    public Map<String, String> setHeader(){
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("ServiceBusNotification-Format","First messeage to NotificationHub");
        mapHeader.put("mobile","9818515510");
        mapHeader.put("DeviceId","IMEI21631527125172");
        return mapHeader;
    }
    public String messageBody(String message){

        Map<String, String> mapBody = new HashMap<>();
        try {
            mapBody.put("message", message);
            mapBody.put("mobile", "9818515510");
            mapBody.put("DeviceId", "IMEI21631527125172");
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString( mapBody );
        }catch (Exception e){
            System.err.println("Exception Cause :"+e.getCause()+"\t Exception Message: "+e.getMessage());
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }







}

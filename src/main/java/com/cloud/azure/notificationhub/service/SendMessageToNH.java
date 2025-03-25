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
            NotificationHubClient notificationHubClient = new NotificationHub(NH_CONNECTION_STRING_SDK, NH_NAME);

            //notificationHubClient.createRegistration();
            if(regCount==0) {
                System.err.println("Create Registration:\t" + createRegistration(notificationHubClient));
                regCount++;
            }
            Notification notification = new Notification();

            notification.setBody(messageBody(message));
            NotificationOutcome outcome = notificationHubClient.sendNotification(notification);
            System.err.println("getTrackingId:\t "+outcome.getTrackingId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String createRegistration( NotificationHubClient nHClient) throws NotificationHubsException {
        try{
            return nHClient.createRegistrationId();
        }catch (Exception e){
            System.err.println("Exception Cause :"+e.getCause()+"\t Exception Message: "+e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
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



    @Scheduled(fixedRate = 5000 )
    public String fetchMessage(){
        System.err.println("\n\nDateTime:\t"+ LocalDateTime.now());
        // String eventData = receiverService.receiveMessages();
        String eventData = "Event Data for notification Hub";
        sendNotificationWithSDK(eventData);
        return eventData;
    }




}

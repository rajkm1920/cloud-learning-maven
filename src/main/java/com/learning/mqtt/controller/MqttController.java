package com.learning.mqtt.controller;

import com.learning.mqtt.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MqttController {
     @Autowired
     private MqttService mqttService;

//    @Scheduled(fixedRate = 5000 )
     public void publishMessage() {
        String now = LocalDateTime.now().toString();
        String message = "Testing MQTT message: "+now;
        String topic = "test/topic";
        String response = mqttService.messagePublisherWithPahoLib(topic, message);
        System.err.println(response+" at :\t"+now);
     }

//    @Scheduled(fixedRate = 5000 )
    public void receiveMessage() {
        String now = LocalDateTime.now().toString();
        String topic = "test/topic";
        String response = mqttService.messageReceiver(topic);
        System.err.println(response+" at :\t"+now);
    }

}

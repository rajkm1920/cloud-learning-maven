package com.learning.cloud.azure.eventhub.controller;


import com.learning.cloud.azure.eventhub.service.EventHubProducerService;
import com.learning.cloud.azure.eventhub.service.EventHubReceiverService;
import com.learning.cloud.azure.notificationhub.service.SendMessageToNH;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
public class EventHubController {

    private final EventHubProducerService producerService;
    private final EventHubReceiverService receiverService;
    private final SendMessageToNH notificationService;

    public EventHubController(EventHubProducerService producerService, EventHubReceiverService receiverService, SendMessageToNH notificationService) {
        this.producerService = producerService;
        this.receiverService = receiverService;
        this.notificationService = notificationService;
    }

    @GetMapping("/send")
    public String sendMessages() {
        String message ="message for EventHub to Notification Hub:"+ LocalDateTime.now();
        producerService.sendMessages(message);
        return "Messages sent!";
    }
    @GetMapping("/receive")
    public String receiveMessages() {
        receiverService.receiveMessages();
        return "Receiving messages. Check console output.";
    }


}

package com.learning.cloud.azure.eventhub.service;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventHubProducerService {

    @Value("${azure.eventhub.connectionstring}")
    private String EVENT_HUB_CONNECTION_STR;

    @Value("${azure.eventhub.name}")
    private String EVENT_HUB_NAME ;

    public void sendMessages(String message) {
        try (EventHubProducerClient producerClient1 = new EventHubClientBuilder()
                .connectionString(EVENT_HUB_CONNECTION_STR, EVENT_HUB_NAME)
                .buildProducerClient()) {
            EventDataBatch eventDataBatch1 = producerClient1.createBatch();
            eventDataBatch1.tryAdd(new EventData(message));
            producerClient1.send(eventDataBatch1);
            System.out.println("Messages sent successfully to Event Hubs:"+EVENT_HUB_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

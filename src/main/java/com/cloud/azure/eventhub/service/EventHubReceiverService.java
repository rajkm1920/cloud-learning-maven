package com.cloud.azure.eventhub.service;
import com.azure.core.util.IterableStream;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.azure.messaging.eventhubs.EventHubConsumerClient;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;


@Service
public class EventHubReceiverService {

    @Value("${azure.eventhub.connectionstring}")
    private String eventHubConnectionString;
    @Value("${azure.eventhub.name}")
    private String eventHubName;
    private static final String CONSUMER_GROUP = "$Default";

    public String receiveMessages() {

        String eventData ="";
        EventHubConsumerClient consumerClient = new EventHubClientBuilder()
                .connectionString(eventHubConnectionString, eventHubName)
                .consumerGroup(CONSUMER_GROUP).buildConsumerClient();

        Instant timeDuration = Instant.now().minus(Duration.ofMinutes(30));
        EventPosition startingPosition = EventPosition.fromEnqueuedTime(timeDuration);

        String partitionId = "0";
        IterableStream<PartitionEvent> events = consumerClient.receiveFromPartition(partitionId, 100,
                startingPosition, Duration.ofSeconds(30));

        Long lastSequenceNumber = -1L;
        for (PartitionEvent partitionEvent : events) {
            // For each event, perform some sort of processing.
            System.out.print("Event received: " + partitionEvent.getData().getSequenceNumber());

            eventData = partitionEvent.getData().getBodyAsString();
            System.out.print("partitionEvent.getData().getBodyAsString(): " + partitionEvent.getData().getBodyAsString());
            lastSequenceNumber = partitionEvent.getData().getSequenceNumber();
            System.out.print("lastSequenceNumber: " + lastSequenceNumber);
        }
        return eventData;
    }

    public void recieveAsyncEventData(){
        EventHubConsumerAsyncClient consumerAsyncClient = new EventHubClientBuilder()
                .connectionString(eventHubConnectionString, eventHubName)
                .consumerGroup(CONSUMER_GROUP).buildAsyncConsumerClient();

        consumerAsyncClient.receive().subscribe(event -> {
            String message = event.getData().getBodyAsString();
            System.out.printf("Received from Event Hub: %s%n", message);

        }, error -> {
            System.err.println("Error receiving from Event Hub: " + error);
        });
    }

}

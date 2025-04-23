package com.learning.cloud.azure.eventhub.config;/*
package com.azure_eventhub.EventHub.config;

import com.azure.spring.integration.core.handler.DefaultMessageHandler;
import com.azure.spring.integration.eventhubs.inbound.EventHubsInboundChannelAdapter;
import com.azure.spring.messaging.eventhubs.core.EventHubsProcessorFactory;
import com.azure.spring.messaging.eventhubs.core.EventHubsTemplate;
import com.azure.spring.messaging.eventhubs.core.checkpoint.CheckpointConfig;
import com.azure.spring.messaging.eventhubs.core.checkpoint.CheckpointMode;
import com.azure.spring.messaging.eventhubs.core.listener.EventHubsMessageListenerContainer;
import com.azure.spring.messaging.eventhubs.core.properties.EventHubsContainerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Configuration
public class EventHubConfig {
    private static final String INPUT_CHANNEL = "input";
    private static final String OUTPUT_CHANNEL = "output";
    private static final String EVENTHUB_NAME = "poc-eventhub";
    private static final String CONSUMER_GROUP = "$Default";
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHubConfig.class);


    @ServiceActivator(inputChannel = INPUT_CHANNEL)
    public void messageReceiver(byte[] payload) {
        String message = new String(payload);
        LOGGER.info("New message received: {}", message);
    }

    @Bean
    public EventHubsMessageListenerContainer messageListenerContainer(EventHubsProcessorFactory processorFactory) {
        EventHubsContainerProperties containerProperties = new EventHubsContainerProperties();
        containerProperties.setEventHubName(EVENTHUB_NAME);
        containerProperties.setConsumerGroup(CONSUMER_GROUP);
        containerProperties.setCheckpointConfig(new CheckpointConfig(CheckpointMode.MANUAL));
        return new EventHubsMessageListenerContainer(processorFactory, containerProperties);
    }

    @Bean
    public EventHubsInboundChannelAdapter messageChannelAdapter(@Qualifier(INPUT_CHANNEL) MessageChannel inputChannel,
                                                                EventHubsMessageListenerContainer listenerContainer) {
        EventHubsInboundChannelAdapter adapter = new EventHubsInboundChannelAdapter(listenerContainer);
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }

    @Bean
    public MessageChannel input() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = OUTPUT_CHANNEL)
    public MessageHandler messageSender(EventHubsTemplate eventHubsTemplate) {
        DefaultMessageHandler handler = new DefaultMessageHandler(EVENTHUB_NAME, eventHubsTemplate);
        handler.setSendCallback(new ListenableFutureCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                LOGGER.info("Message was sent successfully.");
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("There was an error sending the message.", ex);
            }
        });

        return handler;
    }

}
*/

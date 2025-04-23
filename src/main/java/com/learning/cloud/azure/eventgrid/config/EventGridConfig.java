package com.learning.cloud.azure.eventgrid.config;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class EventGridConfig {

    // For custom event
//    EventGridPublisherClient<BinaryData> customEventClient = new EventGridPublisherClientBuilder()
//            .endpoint("https://fedex-custom-topic.eastus-1.eventgrid.azure.net/api/events")
//            .credential(new AzureKeyCredential("23Fe3ljHTGnoLtv6FTzetP61HFPwYD0f7ygsjFh2aKiWJqtDgGRCJQQJ99BDACYeBjFXJ3w3AAABAZEGGOul"))
//            .buildCustomEventPublisherClient();
}

package com.learning.mqtt.service;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.learning.mqtt.config.MqttClientConfig;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class MqttService {

    @Autowired
    MqttClientConfig mqttPublisherConfig;
    /**
     * This class is used to publish messages to the MQTT broker.
     * It uses the MqttPublisherConfig class to get the MQTT client configuration.
     */
    public String messagePublisherWithPahoLib(String topic, String message){
        String response = "";
        try{
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(2);
            mqttPublisherConfig.getIMqttClient().publish(topic, mqttMessage);
            response = "Message published successfully";
        }catch (Exception e){
            response = "Error publishing message: " + e.getMessage();
        }
        return response;
    }
    public String messageReceiver(String topic){
        String message = "";
        try{
            mqttPublisherConfig.getIMqttClient().subscribe(topic);
            mqttPublisherConfig.getIMqttClient().setCallback(callBack());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return message;
    }

    public MqttCallback callBack(){
        return new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.err.println("Connection lost: " + cause.getMessage());
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("Message received: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Delivery complete: " + token.getMessageId());
            }
        };

    }
    /**
     * This method is used to publish messages to the MQTT broker.
     * It uses the MqttPublisherConfig class to get the MQTT client configuration.
     *
     * @param routeId
     * @param ddMessage
     */
    public void messagePublisher(String  routeId, String ddMessage){
        try {
            String topic = routeId+"/topic";

            mqttPublisherConfig.getHiveMQWithMqttClient().publishWith()
                    .topic(topic)
                    .qos(MqttQos.EXACTLY_ONCE)
                    .payload(UTF_8.encode(ddMessage))
                    .send();
            System.out.print("Message published successfully to topic: "+topic+",\t Message :"+ddMessage);
        } catch (Exception e) {
            System.err.print("Failed to publish message: " + e.getMessage());
        }
    }

}

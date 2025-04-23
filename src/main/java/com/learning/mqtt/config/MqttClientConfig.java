package com.learning.mqtt.config;

import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class MqttClientConfig {


    String clientId = "fedex-client";// ClientId should be unique

    @Value("${mqtt.paho.hosturl}")
    String broker;

    @Value("${mqtt.hivemqLib.hosturl}")
    String hosturl;

    @Value("${mqtt.hivemqLib.port}")
    int port;

    @Value("${mqtt.username}")
    String username ;

    @Value("${mqtt.password}")
    String password;

    MemoryPersistence persistence = new MemoryPersistence();

    @Bean
    public IMqttClient getIMqttClient() throws MqttException {
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(15);
            connOpts.setConnectionTimeout(30);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            mqttClient.setCallback(setCallback());
            mqttClient.connect(connOpts);
            System.out.println("MQTT Client Connected with Apache Paho Lib broker : "+broker);
            return mqttClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public MqttCallback setCallback() {
        MqttCallback mqttCallback = new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.err.println("connectionLost: " + throwable.getMessage());
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.err.println(s+ "Message received: " + new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                System.err.println("deliveryComplete: " + iMqttDeliveryToken.isComplete());
            }
        };
        return mqttCallback;
    }

    /**
     * This method creates a HiveMQ MQTT client with SSL configuration.
     * It connects to the MQTT broker using the provided host URL, port, username, and password.
     *
     * @return Mqtt5BlockingClient
     */
    @Bean
    public Mqtt5BlockingClient getHiveMQWithMqttClient() {
        com.hivemq.client.mqtt.MqttClient mqttClient = null;
        try {
            Mqtt5BlockingClient client = com.hivemq.client.mqtt.MqttClient.builder()
                    .useMqttVersion5()
                    .serverHost(hosturl)
                    .serverPort(port)
                    .sslWithDefaultConfig()
                    .buildBlocking();

            client.connectWith()
                    .simpleAuth()
                    .username(username)
                    .password(UTF_8.encode(password))
                    .applySimpleAuth()
                    .send();
            System.out.println("MQTT Client Connected with HiveMQLib broker : "+hosturl);
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

package com.cloud.gcp.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

@Configuration
public class FCMConfig {

    public FirebaseOptions buildFirebaseOption() {
       FirebaseOptions options = null;
        try {
            File configFile = ResourceUtils.getFile("classpath:fedexpushnotification.json");
            FileInputStream serviceAccount = new FileInputStream(configFile);
             options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
             return options;

        }catch(Exception e){
            e.printStackTrace();
        }
        return options;
    }

    @Bean
    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
    @Bean
    FirebaseApp firebaseApp() {
        return FirebaseApp.initializeApp(buildFirebaseOption());
    }



}

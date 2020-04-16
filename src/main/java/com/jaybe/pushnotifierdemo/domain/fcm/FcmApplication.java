package com.jaybe.pushnotifierdemo.domain.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.jaybe.pushnotifierdemo.domain.message.PushNotificationSender;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
class FcmApplication implements PushNotificationSender {

    private FcmInitConfigEntity fcmInitConfigEntity;
    private FirebaseApp firebaseApp;

    public FcmApplication(FcmInitConfigEntity fcmInitConfigEntity) throws Exception {
        this.fcmInitConfigEntity = fcmInitConfigEntity;
        initializeApp();
    }

    private void initializeApp() throws Exception{
        var testController = new TestController();
        try {
            var firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(fcmInitConfigEntity.configStream())).build();

            FirebaseApp.getApps()
                    .stream()
                    .filter(firebaseApp -> firebaseApp.getName().equalsIgnoreCase(fcmInitConfigEntity.getAppName()))
                    .findFirst()
                    .ifPresentOrElse(firebaseApp -> this.firebaseApp = firebaseApp,
                            () -> firebaseApp = FirebaseApp.initializeApp(firebaseOptions, fcmInitConfigEntity.getAppName()));
        } catch (Exception e) {
            log.error("Broken config file! Message={}", e.getLocalizedMessage(), e);
            throw e;
        }
    }

    public String getAppName() {
        return this.fcmInitConfigEntity.getAppName();
    }

    @Override
    public String sendToDevice(String deviceToken, Map<String, String> payload) {
        var firebaseMessaging = FirebaseMessaging.getInstance(this.firebaseApp);

        var message = Message.builder()
                .setToken(deviceToken)
                .setNotification(Notification.builder()
                        .setTitle(payload.get("title"))
                        .setBody(payload.get("body"))
                        //.setImage("https://www.topcream.ru/images/product_images/popup_images/13361_0.jpg")
                        .build())
                .build();



        try {
            var response = firebaseMessaging.send(message);
            log.info("Message sent successfully. Received messageId={}", response);
            // should publish the domain event about success sending message to client
            return response;
        } catch (FirebaseMessagingException e) {
            log.error("Error was occur when trying to send push message. Error Message={}", e.getLocalizedMessage(), e);
            // should publish the domain event about failure sending message to client
            return e.getLocalizedMessage();
        }
    }
}

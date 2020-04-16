package com.jaybe.pushnotifierdemo.domain.message;

import java.util.Map;

public interface PushNotificationSender {

    String sendToDevice(String deviceToken, Map<String, String> payload);

}

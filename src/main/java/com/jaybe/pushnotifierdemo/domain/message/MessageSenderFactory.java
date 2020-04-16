package com.jaybe.pushnotifierdemo.domain.message;

import com.jaybe.pushnotifierdemo.domain.fcm.FcmApplicationsContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class MessageSenderFactory {

    private final FcmApplicationsContainer fcmApplicationsContainer;

    public MessageSenderFactory(FcmApplicationsContainer fcmApplicationsContainer) {
        this.fcmApplicationsContainer = fcmApplicationsContainer;
    }

    public Optional<PushNotificationSender> getInstanceByAppName(final String appName) {
        return Optional.ofNullable(fcmApplicationsContainer.getFcmAppByName(appName)
                .orElse(null));
    }

}

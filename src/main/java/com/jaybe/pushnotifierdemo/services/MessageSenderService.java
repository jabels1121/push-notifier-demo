package com.jaybe.pushnotifierdemo.services;

import com.jaybe.pushnotifierdemo.domain.message.MessageSenderFactory;
import com.jaybe.pushnotifierdemo.domain.message.PushInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class MessageSenderService {

    private final MessageSenderFactory messageSenderFactory;

    public MessageSenderService(MessageSenderFactory messageSenderFactory) {
        this.messageSenderFactory = messageSenderFactory;
    }

    public String sendMessageToDevice(PushInfoDTO pushInfoDTO) {
        AtomicReference<String> feedbackMessage = new AtomicReference<>();
        messageSenderFactory.getInstanceByAppName(pushInfoDTO.getAppName())
                .ifPresentOrElse(pushNotificationSender -> {
                    feedbackMessage.set(pushNotificationSender.sendToDevice(pushInfoDTO.getDeviceToken(),
                            pushInfoDTO.getPayload()));
                }, () -> {
                    log.error("Not found any fcm application by name={}", pushInfoDTO.getAppName());
                    feedbackMessage.set("Not found any fcm application by name=" + pushInfoDTO.getAppName());
                });
        return feedbackMessage.get();
    }
}

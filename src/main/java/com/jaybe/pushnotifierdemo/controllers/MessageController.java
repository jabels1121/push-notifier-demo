package com.jaybe.pushnotifierdemo.controllers;

import com.jaybe.pushnotifierdemo.domain.message.PushInfoDTO;
import com.jaybe.pushnotifierdemo.services.MessageSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@Slf4j
public class MessageController {

    private final MessageSenderService messageSenderService;

    public MessageController(MessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
    }

    @PostMapping(path = "/send", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public String sendMessage(@RequestBody PushInfoDTO pushInfoDTO) {
        return messageSenderService.sendMessageToDevice(pushInfoDTO);
    }
}

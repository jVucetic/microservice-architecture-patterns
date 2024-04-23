package com.example.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {

    @JmsListener(destination = "AppointmentQueue")
    public void messageListener() {
        log.info("message received ");
    }
}

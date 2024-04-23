package com.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AppointmentsController {
    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Appointments!";
    }

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage() {
        try {
            log.info("start");
            jmsTemplate.convertAndSend("AppointmentQueue", "message");

            return new ResponseEntity<>("Sent", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Outlook!";
    }
}

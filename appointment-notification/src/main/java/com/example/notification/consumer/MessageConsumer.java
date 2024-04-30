package com.example.notification.consumer;

import com.example.notification.enums.Status;
import com.example.notification.model.Notification;
import com.example.notification.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {
    private final ObjectMapper objectMapper;
    private final NotificationRepository notificationRepository;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "OrchestratorQueue")
    @SneakyThrows(JsonProcessingException.class)
    public void orchestratorMessage(String message) {
        Notification receivedNotification = objectMapper.readValue(message, Notification.class);
        log.info("received notification with status {} ", receivedNotification.getStatus());

        Notification notification = Notification.builder()
                .content(receivedNotification.getContent())
                .appointmentId(receivedNotification.getAppointmentId())
                .status(receivedNotification.getStatus())
                .build();

        notificationRepository.save(notification);
        log.info("{} notification saved", notification.getStatus());

        if (notification.getStatus() == Status.PENDING) {
            jmsTemplate.convertAndSend("NotificationsQueue", objectMapper.writeValueAsString(notification));
        }


    }
}

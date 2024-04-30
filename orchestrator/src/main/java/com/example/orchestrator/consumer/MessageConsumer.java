package com.example.orchestrator.consumer;

import com.example.orchestrator.entity.Appointment;
import com.example.orchestrator.entity.Notification;
import com.example.orchestrator.enums.Status;
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
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "AppointmentQueue")
    @SneakyThrows(JsonProcessingException.class)
    public void appointmentListener(String message) {
        Appointment appointment = objectMapper.readValue(message, Appointment.class);
        log.info("appointment with id {} received to orchestrator", appointment.getId());
        try {
            // send notification - request for appointment received
            Notification notification = Notification.builder()
                    .appointmentId(appointment.getId())
                    .content("We have received your appointment request.")
                    .status(Status.PENDING)
                    .build();

            jmsTemplate.convertAndSend("OrchestratorQueue", objectMapper.writeValueAsString(notification));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @JmsListener(destination = "NotificationsQueue")
    @SneakyThrows(JsonProcessingException.class)
    public void notificationsListener(String message) {
        log.info("message received ");
        Notification notification = objectMapper.readValue(message, Notification.class);
        try {
            // confirm appointment
            log.info("confirm appointment {} ", notification.getAppointmentId());
            Appointment confirmAppointment = Appointment.builder()
                    .id(notification.getAppointmentId())
                    .status(Status.CONFIRMED)
                    .build();
            jmsTemplate.convertAndSend("ManageAppointmentQueue", objectMapper.writeValueAsString(confirmAppointment));

            //send confirmation notification
            log.info("Sending confirmation Notification");
            Notification confirmationNotification = Notification.builder()
                    .content("Appointment scheduled successfully")
                    .appointmentId(confirmAppointment.getId())
                    .status(Status.CONFIRMED)
                    .build();
            jmsTemplate.convertAndSend("OrchestratorQueue", objectMapper.writeValueAsString(confirmationNotification));

        } catch (Exception e) {
            log.info("canceling appointment {} ", notification.getAppointmentId());
            // cancel appointment
            Appointment cancelAppointment = Appointment.builder()
                    .id(notification.getAppointmentId())
                    .status(Status.CANCELED)
                    .build();
            jmsTemplate.convertAndSend("ManageAppointmentQueue", objectMapper.writeValueAsString(cancelAppointment));

            log.info("sending cancellation notification");
            //send cancel notification
            Notification cancelNotification = Notification.builder()
                    .appointmentId(notification.getAppointmentId())
                    .content("Oops something went wrong. Your appointment has been canceled.")
                    .status(Status.CANCELED)
                    .build();

            jmsTemplate.convertAndSend("OrchestratorQueue", objectMapper.writeValueAsString(cancelNotification));

            log.error(e.getMessage());
        }
    }
}

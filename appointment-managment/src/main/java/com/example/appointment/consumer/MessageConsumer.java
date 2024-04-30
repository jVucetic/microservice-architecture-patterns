package com.example.appointment.consumer;

import com.example.appointment.model.Appointment;
import com.example.appointment.repository.AppointmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {
    private final ObjectMapper objectMapper;
    private final AppointmentRepository appointmentRepository;

    @JmsListener(destination = "ManageAppointmentQueue")
    @SneakyThrows(JsonProcessingException.class)
    public void manageAppointment(String message) {
        Appointment receivedAppointment = objectMapper.readValue(message, Appointment.class);
        log.info("received appointment with id {} and status {}", receivedAppointment.getId(), receivedAppointment.getStatus() );

        Appointment appointment = appointmentRepository.findById(receivedAppointment.getId()).orElseThrow();
        appointment.setStatus(receivedAppointment.getStatus());
        appointmentRepository.save(appointment);

        log.info("appointment updated");
    }

}

package com.example.appointment.controller;

import com.example.appointment.model.Appointment;
import com.example.appointment.repository.AppointmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentsController {
    private final AppointmentRepository appointmentRepository;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Appointments!";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
        try {
            log.info("create appointment starting...");
            Appointment pendingAppointment = Appointment.builder()
                    .startTime(appointment.getStartTime())
                    .endTime(appointment.getEndTime())
                    .build();
            appointmentRepository.save(pendingAppointment);
            jmsTemplate.convertAndSend("AppointmentQueue", objectMapper.writeValueAsString(pendingAppointment));

            return new ResponseEntity<>("Appointment Created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

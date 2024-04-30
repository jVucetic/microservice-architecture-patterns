package com.example.appointment.model;

import com.example.appointment.enums.Status;
import com.example.appointment.util.UUIDGenerator;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Appointment(UUID id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id != null ? id : UUIDGenerator.generateUUID();
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = Status.PENDING;
    }
}

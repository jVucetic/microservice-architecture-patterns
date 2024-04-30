package com.example.notification.model;

import com.example.notification.enums.Status;
import com.example.notification.util.UUIDGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "appointment_id")
    private UUID appointmentId;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Status status;

    @Builder
    public Notification(UUID id, UUID appointmentId, String content, Status status) {
        this.id = id != null ? id : UUIDGenerator.generateUUID();
        this.appointmentId = appointmentId;
        this.content = content;
        this.status = status;
    }
}

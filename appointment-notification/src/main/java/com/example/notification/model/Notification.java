package com.example.notification.model;

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

    @Column(name = "description")
    private String description;

    @Builder
    public Notification(UUID id, String description) {
        this.id = id != null ? id : UUIDGenerator.generateUUID();
        this.description = description;
    }
}

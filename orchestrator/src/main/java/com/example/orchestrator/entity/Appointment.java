package com.example.orchestrator.entity;

import com.example.orchestrator.enums.Status;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Appointment implements Serializable {
    private UUID id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
}

package com.example.orchestrator.entity;

import com.example.orchestrator.enums.Status;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;
@Builder
@Getter
public class Notification {
    private UUID appointmentId;
    private String content;
    private Status status;
}

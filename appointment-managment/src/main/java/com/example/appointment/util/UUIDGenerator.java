package com.example.appointment.util;

import java.util.UUID;

public final class UUIDGenerator {

    private UUIDGenerator() {}

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }
}

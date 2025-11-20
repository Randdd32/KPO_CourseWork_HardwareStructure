package com.hardware.hardware_structure.core.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Formatter {
    private static final DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_INSTANT;

    private static final DateTimeFormatter customDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return isoFormatter.format(date.toInstant());
    }

    public static String formatToCustomString(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(customDateTimeFormatter);
    }

    public static Date parse(String date) {
        try {
            Instant instant = Instant.parse(date);
            return Date.from(instant);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + date);
        }
    }
}

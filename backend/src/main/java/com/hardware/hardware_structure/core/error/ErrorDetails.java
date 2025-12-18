package com.hardware.hardware_structure.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetails {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant timestamp;
    private String error;
    private String message;
    private String details;
    private Map<String, String> fieldErrors;

    public ErrorDetails(String error, String message, String details) {
        this.timestamp = Instant.now();
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public ErrorDetails(String error, String message, String details, Map<String, String> fieldErrors) {
        this.timestamp = Instant.now();
        this.error = error;
        this.message = message;
        this.details = details;
        this.fieldErrors = fieldErrors;
    }
}

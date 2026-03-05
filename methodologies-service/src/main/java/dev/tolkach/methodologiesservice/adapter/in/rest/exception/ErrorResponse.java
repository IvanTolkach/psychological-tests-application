package dev.tolkach.methodologiesservice.adapter.in.rest.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@JsonPropertyOrder({
        "message",
        "path",
        "timestamp",
        "status",
        "error"
})
public class ErrorResponse {
    private final ZonedDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse(HttpStatus httpStatus, String message, String path) {
        this.timestamp = ZonedDateTime.now(ZoneId.of("UTC"));
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}

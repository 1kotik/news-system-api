package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private int code;
    private String error;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime timestamp;
    private Map<String, String> errors = new HashMap<>();

    public ErrorResponse(int code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.timestamp = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public ErrorResponse(int code, String error, String message, Map<String, String> errors) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.timestamp = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.errors = errors;
    }
}

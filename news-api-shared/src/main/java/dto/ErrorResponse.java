package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private int code;
    private String error;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime timestamp;

    public ErrorResponse(int code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.timestamp = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}

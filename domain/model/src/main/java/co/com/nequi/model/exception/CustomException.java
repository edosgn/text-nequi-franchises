package co.com.nequi.model.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final String code;
    private final String message;
    private final LocalDateTime date;
}

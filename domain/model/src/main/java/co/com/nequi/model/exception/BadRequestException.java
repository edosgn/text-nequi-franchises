package co.com.nequi.model.exception;

import co.com.nequi.model.enums.TechnicalMessage;

import java.time.LocalDateTime;

public class BadRequestException extends CustomException {
    public BadRequestException() {
        super(TechnicalMessage.BAD_REQUEST.getCode(), TechnicalMessage.BAD_REQUEST.getMessage(), LocalDateTime.now());
    }
}

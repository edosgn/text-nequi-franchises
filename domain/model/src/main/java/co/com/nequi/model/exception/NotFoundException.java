package co.com.nequi.model.exception;

import co.com.nequi.model.enums.TechnicalMessage;

import java.time.LocalDateTime;

public class NotFoundException extends CustomException {
    public NotFoundException() {
        super(TechnicalMessage.NOT_FOUND.getCode(), TechnicalMessage.NOT_FOUND.getMessage(), LocalDateTime.now());
    }
}

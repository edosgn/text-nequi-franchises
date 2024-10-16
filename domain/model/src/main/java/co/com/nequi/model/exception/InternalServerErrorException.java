package co.com.nequi.model.exception;

import co.com.nequi.model.enums.TechnicalMessage;

import java.time.LocalDateTime;

public class InternalServerErrorException extends CustomException {
    public InternalServerErrorException() {
        super(TechnicalMessage.INTERNAL_SERVER_ERROR.getCode(), TechnicalMessage.INTERNAL_SERVER_ERROR.getMessage(), LocalDateTime.now());
    }
}

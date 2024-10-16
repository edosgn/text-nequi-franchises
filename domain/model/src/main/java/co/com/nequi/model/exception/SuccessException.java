package co.com.nequi.model.exception;

import co.com.nequi.model.enums.TechnicalMessage;

import java.time.LocalDateTime;

public class SuccessException extends CustomException {
    public SuccessException() {
        super(TechnicalMessage.SUCCESS.getCode(), TechnicalMessage.SUCCESS.getMessage(), LocalDateTime.now());
    }
}

package co.com.nequi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TechnicalMessage {
    SUCCESS("200", "Success"),
    BAD_REQUEST("400", "Bad request"),
    NOT_FOUND("404", "Not found"),
    INTERNAL_SERVER_ERROR("500", "Internal server error");

    private final String code;
    private final String message;
}

package example.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    STORE_NOT_FOUND(404, "store not found");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}

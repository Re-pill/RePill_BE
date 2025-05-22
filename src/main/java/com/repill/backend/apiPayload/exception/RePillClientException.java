package com.repill.backend.apiPayload.exception;

import com.repill.backend.apiPayload.code.ErrorReasonDTO;
import com.repill.backend.apiPayload.code.status.ErrorStatus;
import lombok.Getter;

@Getter
public class RePillClientException extends RePillException {

    private final ErrorReasonDTO reason;

    public RePillClientException(final ErrorStatus errorStatus) {
        super(errorStatus.getHttpStatus(), errorStatus.getMessage());
        this.reason = errorStatus.getReason();
    }

}

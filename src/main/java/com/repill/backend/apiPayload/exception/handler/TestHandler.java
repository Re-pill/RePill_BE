package com.repill.backend.apiPayload.exception.handler;

import com.repill.backend.apiPayload.code.BaseErrorCode;
import com.repill.backend.apiPayload.exception.GeneralException;

public class TestHandler extends GeneralException {

    public TestHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

}

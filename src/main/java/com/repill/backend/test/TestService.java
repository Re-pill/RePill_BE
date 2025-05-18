package com.repill.backend.test;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.handler.TestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    public void CheckFlag(Integer flag) {
        if(flag == 1)
            throw new TestHandler(ErrorStatus.TEST_EXCEPTION);
    }

}

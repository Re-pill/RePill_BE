package com.repill.backend.apiPayload.code.status;

import com.repill.backend.apiPayload.code.BaseErrorCode;
import com.repill.backend.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 회원 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "존재하지 않는 회원입니다."),

    // 약품 관련 에러
    MEDICINE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "존재하지 않는 약품 타입입니다."),

    // 필요한건 아래처럼 추가해서 사용해주세요.
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    TEST_EXCEPTION(HttpStatus.BAD_REQUEST, "TEST04", "에러 핸들러 테스트");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
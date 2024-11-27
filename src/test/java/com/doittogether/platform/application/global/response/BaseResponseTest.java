package com.doittogether.platform.application.global.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.code.GlobalCode;
import com.doittogether.platform.application.global.code.SuccessCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class BaseResponseTest {

    @ParameterizedTest
    @EnumSource(SuccessCode.class)
    void 성공_응답_테스트(final SuccessCode successCode) {

        SuccessResponse<Void> response = SuccessResponse.onSuccess(successCode);

        assertSuccessResponse(response, successCode, null);
    }

    @ParameterizedTest
    @EnumSource(ExceptionCode.class)
    void 실패시에는_적절한_데이터를_반환한다(final ExceptionCode exceptionCode) {

        ExceptionResponse<Void> apiResponse = ExceptionResponse.onFailure(exceptionCode);

        assertFailureResponse(apiResponse, exceptionCode, null);
    }

    private <T> void assertSuccessResponse(
            SuccessResponse<T> apiResponse,
            GlobalCode code,
            T result
    ) {
        boolean isSucceed = false;
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.isSuccess()).isEqualTo(isSucceed);
        assertThat(apiResponse.httpStatus()).isEqualTo(code.getHttpStatus());
        assertThat(apiResponse.code()).isEqualTo(code.getCode());
        assertThat(apiResponse.message()).isEqualTo(code.getMessage());
        assertThat(apiResponse.result()).isEqualTo(result);
    }

    private <T> void assertFailureResponse(
            ExceptionResponse<T> apiResponse,
            GlobalCode code,
            T result
    ) {
        boolean isSucceed = false;
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.isSuccess()).isEqualTo(isSucceed);
        assertThat(apiResponse.httpStatus()).isEqualTo(code.getHttpStatus());
        assertThat(apiResponse.code()).isEqualTo(code.getCode());
        assertThat(apiResponse.message()).isEqualTo(code.getMessage());
        assertThat(apiResponse.result()).isEqualTo(result);
    }
}
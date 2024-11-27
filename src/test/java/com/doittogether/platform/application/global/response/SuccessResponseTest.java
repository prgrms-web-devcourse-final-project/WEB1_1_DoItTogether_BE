package com.doittogether.platform.application.global.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.doittogether.platform.application.global.code.SuccessCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class SuccessResponseTest {

    @ParameterizedTest
    @EnumSource(SuccessCode.class)
    void 성공_응답_반환값이_없는_경우(SuccessCode successCode) {
        SuccessResponse<Void> response = SuccessResponse.onSuccess(successCode);
        assertCommonSuccessResponse(response, successCode);
        assertThat(response.result()).isEqualTo(null);
    }

    @ParameterizedTest
    @EnumSource(SuccessCode.class)
    void 성공_응답_반환값이_있는_경우(SuccessCode successCode) {
        final String result = "result";
        SuccessResponse<String> response = SuccessResponse.onSuccess(successCode, result);
        assertCommonSuccessResponse(response, successCode);
        assertThat(response.result()).isEqualTo(result);
    }

    @Test
    void 성공_응답_반환값도_없고_상태도_결정할_필요가_없는_경우() {
        SuccessResponse<Void> response = SuccessResponse.onSuccess();
        assertCommonSuccessResponse(response, SuccessCode._OK);
        assertThat(response.result()).isEqualTo(null);
    }

    private <T> void assertCommonSuccessResponse(SuccessResponse<T> actual, SuccessCode expectedCode) {
        assertThat(actual.isSuccess()).isTrue();
        assertThat(actual.httpStatus()).isEqualTo(expectedCode.getHttpStatus());
        assertThat(actual.code()).isEqualTo(expectedCode.getCode());
        assertThat(actual.message()).isEqualTo(expectedCode.getMessage());
    }
}
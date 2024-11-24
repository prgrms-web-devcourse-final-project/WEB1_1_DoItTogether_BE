package com.doittogether.platform.application.global;

import static org.assertj.core.api.Assertions.assertThat;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.code.SuccessCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ApiResponseTest {
    @Mock
    private ApiResponse apiResponse;

    @ParameterizedTest
    @CsvSource(value = {
            "_OK:true:OK:COMMON_200:성공입니다.",
            "_CREATED:true:CREATED:COMMON_201:리소스가 성공적으로 생성되었습니다.",
            "_NO_CONTENT:true:NO_CONTENT:COMMON_204:콘텐츠가 없습니다."
    }, delimiter = ':')
    void 반환하는_데이터가_있는_경우_성공시에는_응답객체를_반환한다(final SuccessCode successCode, final boolean isSuccess,
                                          final HttpStatus status, final String code, final String message) {
        final String result = "TestResult";
        ResponseEntity<ApiResponse<String>> response = ApiResponse.onSuccess(successCode, result);
        ApiResponse<String> apiResponse = response.getBody();

        assertApiResponse(apiResponse, isSuccess, status, code, message, result);
    }


    @Test
    void 반환데이터가_없는_경우_성공시에는_SuccessCode를_기반으로_응답객체를_반환한다() {
        ResponseEntity<ApiResponse<Void>> response = ApiResponse.onSuccess();
        ApiResponse<Void> apiResponse = response.getBody();

        assertApiResponse(
                apiResponse,
                true,
                SuccessCode._OK.getHttpStatus(),
                SuccessCode._OK.getCode(),
                SuccessCode._OK.getMessage(),
                null
        );
    }

    @ParameterizedTest
    @EnumSource(ExceptionCode.class)
    void 실패시에는_적절한_데이터를_반환한다(final ExceptionCode exceptionCode) {
        ApiResponse<Void> apiResponse = ApiResponse.onFailure(exceptionCode);

        assertApiResponse(
                apiResponse,
                false,
                exceptionCode.getHttpStatus(),
                exceptionCode.getCode(),
                exceptionCode.getMessage(),
                null
        );
    }

    private <T> void assertApiResponse(
            ApiResponse<T> apiResponse,
            boolean isSuccess,
            HttpStatus status,
            String code,
            String message,
            T result
    ) {
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.isSuccess()).isEqualTo(isSuccess);
        assertThat(apiResponse.httpStatus()).isEqualTo(status);
        assertThat(apiResponse.code()).isEqualTo(code);
        assertThat(apiResponse.message()).isEqualTo(message);
        assertThat(apiResponse.result()).isEqualTo(result);
    }
}
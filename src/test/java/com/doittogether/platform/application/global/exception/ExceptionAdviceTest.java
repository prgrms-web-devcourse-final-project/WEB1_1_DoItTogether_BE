package com.doittogether.platform.application.global.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.housework.HouseworkException;
import com.doittogether.platform.application.global.response.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

@WebMvcTest(ExceptionAdvice.class)
class ExceptionAdviceTest {

    @Autowired
    private ExceptionAdvice exceptionAdvice;

    @ParameterizedTest
    @EnumSource(ExceptionCode.class)
    void expected_익셉션을_처리한다(ExceptionCode exceptionCode) {
        HouseworkException exception = new HouseworkException(exceptionCode);
        ResponseEntity<ExceptionResponse<Void>> response = exceptionAdvice.handleGeneralException(exception);
        assertExceptionResponse(response, exception.getExceptionCode());
    }

    @Test
    void 유효성_검증_실패_익셉션을_처리한다() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        ResponseEntity<ExceptionResponse<Void>> response = exceptionAdvice.handleValidException(exception);
        assertExceptionResponse(response, ExceptionCode.NOT_VALIDATE_FILED);
    }

    private void assertExceptionResponse(ResponseEntity<ExceptionResponse<Void>> expected,
                                         ExceptionCode exceptionCode) {
        assertThat(expected.getStatusCode()).isEqualTo(exceptionCode.getHttpStatus());
        assertThat(expected.getBody()).isNotNull();
        assertThat(expected.getBody().isSuccess()).isFalse();
        assertThat(expected.getBody().message()).contains(exceptionCode.getMessage());
    }
}

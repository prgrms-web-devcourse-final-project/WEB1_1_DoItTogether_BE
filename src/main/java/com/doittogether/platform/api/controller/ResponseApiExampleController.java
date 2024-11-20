package com.doittogether.platform.api.controller;

import com.doittogether.platform.global.ApiResponse;
import com.doittogether.platform.global.exception.ErrorStatus;
import com.doittogether.platform.global.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 다양한 형태의 Response API 활용 방법 예제 제공
 * ※ 다음과 같이 프론트에게 매핑하여 전달합니다!
 *
 * @author ycjung
 */
@RestController
@RequestMapping("/api/v1/exception")
public class ResponseApiExampleController {

    @GetMapping("/success") // 일반적인 성공 처리
    public ResponseEntity<ApiResponse<Object>> shouldReturnSuccess() {
        List<Object> result = List.of(1, "Hello", 3.14, true, new int[]{1, 2, 3});
        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/success-custom") // 커스텀 성공 처리
    public ResponseEntity<ApiResponse<String>> shouldReturnSuccessCustom() {
        String result = "성공적인 커스텀 생성";
        return ApiResponse.onSuccess(HttpStatus.CREATED, "COMMON_201", "생성일 경우", result);
    }

    @GetMapping("/error-custom") // 커스텀 실패 처리
    public ResponseEntity<ApiResponse<Void>> shouldReturnErrorCustom() {
        if(true) {
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR); // 커스텀 예외 발생
            // 또는 아래와 같이 커스텀 가능
            // throw new GeneralException(ErrorStatus._BAD_REQUEST);
            // throw new GeneralException(ErrorStatus.CHORE_NOT_FOUND);
        }

        return ApiResponse.onSuccess();
    }

    @GetMapping("/null-pointer") // NullPointerException 발생
    public ResponseEntity<ApiResponse<Void>> shouldReturnNullPointerException() {
        String str = null;
        if(str.equals("test")) { // str이 null이므로 NullPointerException 발생
            return ApiResponse.onSuccess();
        }

        return ApiResponse.onSuccess();
    }

    @GetMapping("/throw")
    public ResponseEntity<ApiResponse<Void>> shouldReturnThrowException() {
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            throw e; // 최상위 advice 로 던진다.
        }

        return ApiResponse.onSuccess();
    }
}

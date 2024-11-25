package com.doittogether.platform.presentation;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.exception.housework.HouseworkException;
import com.doittogether.platform.application.global.response.BaseResponse;
import com.doittogether.platform.application.global.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 다양한 형태의 Response API 활용 방법 예제 제공 ※ 다음과 같이 프론트에게 매핑하여 전달합니다!
 *
 * @author ycjung
 */
@RestController
@RequestMapping("/api/v1/exception")
public class __ResponseApiExampleController {

    @GetMapping("/success-custom") // 커스텀 성공 처리
    public ResponseEntity<BaseResponse<String>> shouldReturnSuccessCustom() {
        String result = "성공적인 커스텀 생성";
        return ResponseEntity.ok()
                .body(SuccessResponse.onSuccess(SuccessCode._CREATED, result));
    }

    @GetMapping("/error-custom") // 커스텀 실패 처리
    public ResponseEntity<BaseResponse<Void>> shouldReturnErrorCustom() {
        if (true) {
            throw new HouseworkException(ExceptionCode._INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok()
                .body(SuccessResponse.onSuccess(SuccessCode._OK));
    }

    @GetMapping("/null-pointer") // NullPointerException 발생
    public ResponseEntity<BaseResponse<Void>> shouldReturnNullPointerException() {
        String str = null;
        if (str.equals("test")) { // str이 null이므로 NullPointerException 발생
            throw new HouseworkException(ExceptionCode._INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok()
                .body(SuccessResponse.onSuccess(SuccessCode._OK));
    }

    @GetMapping("/throw")
    public ResponseEntity<BaseResponse<Void>> shouldReturnThrowException() {
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            throw new HouseworkException(ExceptionCode._INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok()
                .body(SuccessResponse.onSuccess(SuccessCode._OK));
    }
}

package io.directional.recruitment.exceptionhander

import io.directional.recruitment.common.error.ErrorCode
import io.directional.recruitment.common.api.Api
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception
import java.util.Objects

@RestControllerAdvice
@Order(value = Int.MAX_VALUE) // 가장 마지막에 처리
class GlobalExceptionHandler {

    fun exception(exception: Exception): ResponseEntity<Api<Objects>> {
        return ResponseEntity
            .status(500)
            .body(
                Api.ERROR(ErrorCode.SERVER_ERROR)
            )
    }
}
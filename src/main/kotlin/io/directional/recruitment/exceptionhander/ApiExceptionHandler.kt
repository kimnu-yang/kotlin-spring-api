package io.directional.recruitment.exceptionhander

import io.directional.recruitment.common.api.Api
import io.directional.recruitment.common.exeption.ApiException
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.Objects

@RestControllerAdvice
@Order(value = Int.MIN_VALUE) // 최우선적으로 처리
class ApiExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun apiException(apiException: ApiException): ResponseEntity<Api<Objects>> {
        val errorCode = apiException.errorCodeIfs

        return ResponseEntity
            .status(errorCode.getHttpStatusCode())
            .body(Api.ERROR(errorCode, apiException.errorDescription))
    }
}
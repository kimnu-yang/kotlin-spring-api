package io.directional.recruitment.common.api

import io.directional.recruitment.common.error.ErrorCode
import io.directional.recruitment.common.error.ErrorCodeIfs

data class Result(
    val resultCode: Int?=null,
    val resultMessage: String?=null,
    val resultDescription: String?=null
){
    companion object {
        fun OK(): Result {
            return Result(
                resultCode = ErrorCode.OK.getErrorCode(),
                resultMessage = ErrorCode.OK.getDescription(),
                resultDescription = "성공"
            )
        }

        fun ERROR(errorCodeIfs: ErrorCodeIfs): Result {
            return Result(
                resultCode = errorCodeIfs.getErrorCode(),
                resultMessage = errorCodeIfs.getDescription(),
                resultDescription = "에러"
            )
        }

        fun ERROR(errorCodeIfs: ErrorCodeIfs, description: String): Result {
            return Result(
                resultCode = errorCodeIfs.getErrorCode(),
                resultMessage = errorCodeIfs.getDescription(),
                resultDescription = description
            )
        }
    }

}

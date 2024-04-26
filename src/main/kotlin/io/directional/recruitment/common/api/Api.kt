package io.directional.recruitment.common.api

import io.directional.recruitment.common.error.ErrorCodeIfs

data class Api<T>(
    var result: Result?=null,
    var body: T?=null
){
    companion object {
        fun <T> OK(body: T?): Api<T> {
            return Api(
                result = Result.OK(),
                body = body
            )
        }

        fun <T> ERROR(result: Result): Api<T> {
            return Api(
                result = result
            )
        }

        fun <T> ERROR(errorCodeIfs: ErrorCodeIfs): Api<T> {
            return Api(
                result = Result.ERROR(errorCodeIfs)
            )
        }

        fun <T> ERROR(errorCodeIfs: ErrorCodeIfs, description: String): Api<T> {
            return Api(
                result = Result.ERROR(errorCodeIfs, description)
            )
        }
    }
}
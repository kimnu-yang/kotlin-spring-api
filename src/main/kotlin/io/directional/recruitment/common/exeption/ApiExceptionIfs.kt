package io.directional.recruitment.common.exeption

import io.directional.recruitment.common.error.ErrorCodeIfs

interface ApiExceptionIfs {
    val errorCodeIfs: ErrorCodeIfs?
    val errorDescription: String?
}

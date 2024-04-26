package io.directional.recruitment.common.exeption

import io.directional.recruitment.common.error.ErrorCodeIfs

class ApiException: RuntimeException, ApiExceptionIfs {

    override val errorCodeIfs: ErrorCodeIfs
    override val errorDescription: String

    constructor(errorCodeIfs: ErrorCodeIfs): super(errorCodeIfs.getDescription()){
        this.errorCodeIfs = errorCodeIfs
        this.errorDescription = errorCodeIfs.getDescription()
    }

    constructor(
        errorCodeIfs: ErrorCodeIfs,
        errorCodeDescription: String

    ): super(errorCodeIfs.getDescription()){
        this.errorCodeIfs = errorCodeIfs
        this.errorDescription = errorCodeDescription
    }

    constructor(
        errorCodeIfs: ErrorCodeIfs,
        throwable: Throwable
    ): super(throwable){
        this.errorCodeIfs = errorCodeIfs
        this.errorDescription = errorCodeIfs.getDescription()
    }

    constructor(
        errorCodeIfs: ErrorCodeIfs,
        errorCodeDescription: String,
        throwable: Throwable
    ): super(throwable){
        this.errorCodeIfs = errorCodeIfs
        this.errorDescription = errorCodeDescription
    }
}
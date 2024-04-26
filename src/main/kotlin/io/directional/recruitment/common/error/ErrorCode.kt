package io.directional.recruitment.common.error

enum class ErrorCode (
    private val httpStatusCode: Int,
    private val errorCode: Int,
    private val description: String
): ErrorCodeIfs {

    OK(200, 200, "성공"),
    BAD_REQUEST(400, 400, "잘못된 요청"),
    SERVER_ERROR(500, 500, "서버에러"),
    EMPTY_INDEX(500, 501, "해당 INDEX 데이터 없음"),
    EMPTY_INDEX_LIST(500, 502, "INDEX 데이터 없음"),
    INCORRECT_SORT_VALUE(500, 503, "올바르지 않은 정렬값")
    ;

    override fun getHttpStatusCode(): Int {
        return this.httpStatusCode
    }

    override fun getErrorCode(): Int {
        return this.httpStatusCode
    }

    override fun getDescription(): String {
        return this.description
    }
}

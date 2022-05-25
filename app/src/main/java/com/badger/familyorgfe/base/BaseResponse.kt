package com.badger.familyorgfe.base

enum class ResponseError {
    NONE,
    FAMILY_MEMBER_DOES_NOT_EXIST,
    INVALID_SCANNING_CODE,
    FAMILY_DOES_NOT_EXISTS,
    USER_ALREADY_IN_FAMILY,
    USER_ALREADY_INVITED
}

data class BaseResponse<T>(
    val error: ResponseError = ResponseError.NONE,
    val data: T
) {
    fun hasNoErrors() = error == ResponseError.NONE
}
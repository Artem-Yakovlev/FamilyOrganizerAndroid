package com.badger.familyorgfe.base

enum class ResponseError {
    NONE,
    FAMILY_DOES_NOT_EXISTS
}

data class BaseResponse<T>(
    val error: ResponseError = ResponseError.NONE,
    val data: T
) {
    fun hasNoErrors() = error == ResponseError.NONE
}
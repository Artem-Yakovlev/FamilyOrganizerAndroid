package com.badger.familyorgfe.data.source.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    companion object {
        private const val API = "/auth"
        private const val IS_EMAIL_EXISTS = "$API/isEmailExists"
        private const val SEND_CODE = "$API/sendCode"
        private const val CHECK_CODE = "$API/checkCode"
    }

    @POST(IS_EMAIL_EXISTS)
    suspend fun isEmailExists(@Body form: IsEmailExistsJson.Form): IsEmailExistsJson.Response

    @POST(SEND_CODE)
    suspend fun sendCode(@Body form: SendCodeJson.Form): SendCodeJson.Response

    @POST(CHECK_CODE)
    suspend fun checkCode(@Body form: CheckCodeJson.Form): CheckCodeJson.Response
}
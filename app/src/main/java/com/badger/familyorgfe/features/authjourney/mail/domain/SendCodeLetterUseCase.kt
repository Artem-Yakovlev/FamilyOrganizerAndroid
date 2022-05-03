package com.badger.familyorgfe.features.authjourney.mail.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.auth.AuthApi
import javax.inject.Inject

class SendCodeLetterUseCase @Inject constructor(
    private val api: AuthApi
) : BaseUseCase<String, Boolean>() {

    override suspend fun invoke(arg: String): Boolean {
//        val body = SendCodeJson.Form(
//            email = arg
//        )
//        val response = api.sendCode(body)
//        return response.success
//        delay(100)
        return true
    }
}
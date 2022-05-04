package com.badger.familyorgfe.features.authjourney.mail.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.auth.AuthApi
import com.badger.familyorgfe.data.source.auth.SendCodeJson
import javax.inject.Inject

class SendCodeLetterUseCase @Inject constructor(
    private val api: AuthApi
) : BaseUseCase<String, Boolean>() {

    override suspend fun invoke(arg: String): Boolean {
        val body = SendCodeJson.Form(
            email = arg
        )
        return try {
            api.sendCode(body).success
        } catch (e: Exception) {
            false
        }
    }
}
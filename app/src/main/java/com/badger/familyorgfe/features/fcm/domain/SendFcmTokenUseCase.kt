package com.badger.familyorgfe.features.fcm.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.user.UserApi
import com.badger.familyorgfe.data.source.user.json.SetTokenJson
import javax.inject.Inject

class SendFcmTokenUseCase @Inject constructor(
    val userApi: UserApi
) : BaseUseCase<String, Unit>() {

    override suspend fun invoke(arg: String) {
        val form = SetTokenJson.Form(arg)
        try {
            userApi.setToken(form)
        } catch (e: Exception) {

        }
    }

}
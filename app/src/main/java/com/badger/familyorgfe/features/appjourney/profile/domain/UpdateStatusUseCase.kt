package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.UserStatus
import com.badger.familyorgfe.data.source.user.json.UpdateStatusJson
import com.badger.familyorgfe.data.source.user.UserApi
import javax.inject.Inject

class UpdateStatusUseCase @Inject constructor(
    private val userApi: UserApi
) : BaseUseCase<UserStatus, Unit>() {

    override suspend fun invoke(arg: UserStatus) {
        try {
            userApi.updateStatus(UpdateStatusJson.Form(arg.toString()))
        } catch (e: Exception) {

        }
    }
}
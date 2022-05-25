package com.badger.familyorgfe.features.authjourney.entername.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.user.UserApi
import com.badger.familyorgfe.data.source.user.json.UpdateProfileNameJson
import javax.inject.Inject

class UpdateProfileNameUseCase @Inject constructor(
    private val api: UserApi
) : BaseUseCase<String, Unit>() {

    override suspend fun invoke(arg: String) {
        try {
            api.updateProfileName(UpdateProfileNameJson.Form(arg)).user
        } catch (e: Exception) {

        }
    }
}
package com.badger.familyorgfe.features.authjourney.entername.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.user.UserApi
import kotlinx.coroutines.delay
import javax.inject.Inject

class UpdateProfileNameUseCase @Inject constructor(
    private val api: UserApi
) : BaseUseCase<String, Unit>() {

    override suspend fun invoke(arg: String) {
//        val token = dataStoreRepository.token.firstOrNull()
//        val result = api.updateProfileName(UpdateProfileNameJson.Form(arg))
//        // save user
//        return result.

        delay(500)
    }
}
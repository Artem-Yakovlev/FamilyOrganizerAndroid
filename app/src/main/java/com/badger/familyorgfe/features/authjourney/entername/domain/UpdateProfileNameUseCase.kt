package com.badger.familyorgfe.features.authjourney.entername.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IUserRepository
import com.badger.familyorgfe.data.source.user.json.UpdateProfileNameJson
import com.badger.familyorgfe.data.source.user.UserApi
import javax.inject.Inject

class UpdateProfileNameUseCase @Inject constructor(
    private val api: UserApi,
    private val userRepository: IUserRepository
) : BaseUseCase<String, Unit>() {

    override suspend fun invoke(arg: String) {
        try {
            api.updateProfileName(UpdateProfileNameJson.Form(arg)).user
        } catch (e: Exception) {
            null
        }?.let { userRepository.saveUser(it) }
    }
}
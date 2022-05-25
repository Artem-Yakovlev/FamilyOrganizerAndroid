package com.badger.familyorgfe.features.authjourney.entername.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.repository.IUserRepository
import com.badger.familyorgfe.data.source.user.UserApi
import com.badger.familyorgfe.data.source.user.json.GetProfileJson
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val api: UserApi,
    private val userRepository: IUserRepository
) : BaseUseCase<Unit, User?>() {

    override suspend fun invoke(arg: Unit): User? {
        return try {
            val result = api.getProfile(GetProfileJson.Form())
            result.user
        } catch (e: Exception) {
            null
        }
    }
}
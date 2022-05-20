package com.badger.familyorgfe.commoninteractors

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.source.user.GetProfileJson
import com.badger.familyorgfe.data.source.user.UserApi
import javax.inject.Inject

class GetMainUserUseCase @Inject constructor(
    private val userApi: UserApi
) : BaseUseCase<Unit, User>() {

    override suspend fun invoke(arg: Unit): User {
        return try {
            userApi.getProfile(GetProfileJson.Form()).user ?: throw IllegalStateException()
        } catch (e: Exception) {
            User.createEmpty()
        }

    }
}
package com.badger.familyorgfe.features.authjourney.entername.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.user.UserApi
import kotlinx.coroutines.delay
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val api: UserApi,
    private val dataStoreRepository: IDataStoreRepository
) : BaseUseCase<Unit, User?>() {

    override suspend fun invoke(arg: Unit): User? {
//        val token = dataStoreRepository.token.firstOrNull()
//        val result = api.getProfile(GetProfileJson.Form())
//        return result.user

        delay(500)
        val userId = "userID"
        val fridgeId = "fridge"

        return User(
            id = userId,
            name = "",
            email = "tony.robbing@email.com",
            fridgeId = fridgeId,
            createdAt = LocalDateTime.now(),
            updateAt = LocalDateTime.now(),
            isRegistered = true
        )
    }
}
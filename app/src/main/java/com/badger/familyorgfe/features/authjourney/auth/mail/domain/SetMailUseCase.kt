package com.badger.familyorgfe.features.authjourney.auth.mail.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IUserRepository
import javax.inject.Inject

class SetMailUseCase @Inject constructor(
    private val repository: IUserRepository
) : BaseUseCase<Unit, Boolean>() {

    override suspend fun invoke(arg: Unit): Boolean {
        return false
    }
}
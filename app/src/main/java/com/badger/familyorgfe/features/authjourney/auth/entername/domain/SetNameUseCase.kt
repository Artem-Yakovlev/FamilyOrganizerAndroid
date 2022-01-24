package com.badger.familyorgfe.features.authjourney.auth.entername.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IUserRepository
import javax.inject.Inject

class SetNameUseCase @Inject constructor(
    private val repository: IUserRepository
) : BaseUseCase<String, Boolean>() {

    override suspend fun invoke(arg: String): Boolean {
        return false
    }
}
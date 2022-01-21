package com.badger.familyorgfe.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IUserRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: IUserRepository
) : BaseUseCase<Unit, Boolean>() {

    override suspend fun invoke(arg: Unit): Boolean {
        return false
    }
}
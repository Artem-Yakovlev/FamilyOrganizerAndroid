package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository
) : BaseUseCase<Unit, Unit>() {

    override suspend fun invoke(arg: Unit) {
        dataStoreRepository.clearData()
    }
}
package com.badger.familyorgfe.features.familyauthjourney.all.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import javax.inject.Inject

class SetFamilyIdUseCase @Inject constructor(
    private val dataStore: IDataStoreRepository
) : BaseUseCase<Long, Unit>() {

    override suspend fun invoke(arg: Long) {
        dataStore.setFamilyId(arg)
    }
}
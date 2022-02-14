package com.badger.familyorgfe.commoninteractors

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import javax.inject.Inject

class SetIsUserAuthorizedUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
) : BaseUseCase<Boolean, Unit>() {

    override suspend fun invoke(arg: Boolean) {
        dataStoreRepository.setIsUserAuthorized(arg)
    }
}
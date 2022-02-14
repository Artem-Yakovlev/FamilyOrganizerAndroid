package com.badger.familyorgfe.commoninteractors

import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeIsUserAuthorizedUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
) : FlowUseCase<Unit, Boolean>() {

    override fun invoke(arg: Unit): Flow<Boolean> {
        return dataStoreRepository.isUserAuthorized
    }
}
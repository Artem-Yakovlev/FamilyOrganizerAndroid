package com.badger.familyorgfe.commoninteractors

import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class IsAuthedUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
) : FlowUseCase<Unit, Boolean>() {

    override fun invoke(arg: Unit): Flow<Boolean> {
        return combine(dataStoreRepository.token, dataStoreRepository.userId) { token, userId ->
            token.isNotEmpty() && userId.isNotEmpty()
        }
    }
}
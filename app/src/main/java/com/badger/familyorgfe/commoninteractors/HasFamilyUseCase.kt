package com.badger.familyorgfe.commoninteractors

import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HasFamilyUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository
) : FlowUseCase<Unit, Boolean>() {

    override fun invoke(arg: Unit): Flow<Boolean> {
        return dataStoreRepository.familyId.map { it != null }
    }
}
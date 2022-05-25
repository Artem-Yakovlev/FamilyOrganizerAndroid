package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLocalNamesUseCase @Inject constructor(
    private val userRepository: IUserRepository
) : FlowUseCase<Unit, List<LocalName>>() {

    override fun invoke(arg: Unit): Flow<List<LocalName>> {
        return userRepository.getAllLocalNames()
    }
}
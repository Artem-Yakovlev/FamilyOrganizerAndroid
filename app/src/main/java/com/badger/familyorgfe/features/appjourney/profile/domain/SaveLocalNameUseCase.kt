package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.repository.IUserRepository
import javax.inject.Inject

class SaveLocalNameUseCase @Inject constructor(
    private val userRepository: IUserRepository
) : BaseUseCase<LocalName, Unit>() {

    override suspend fun invoke(arg: LocalName) {
        userRepository.saveLocalName(arg)
    }
}
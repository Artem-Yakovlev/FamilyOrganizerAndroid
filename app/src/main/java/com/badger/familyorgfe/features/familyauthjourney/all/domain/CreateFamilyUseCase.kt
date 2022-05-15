package com.badger.familyorgfe.features.familyauthjourney.all.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import javax.inject.Inject

class CreateFamilyUseCase @Inject constructor() : BaseUseCase<String, FamiliesAndInvites>() {

    override suspend fun invoke(arg: String): FamiliesAndInvites {
        return FamiliesAndInvites.createEmpty()
    }
}
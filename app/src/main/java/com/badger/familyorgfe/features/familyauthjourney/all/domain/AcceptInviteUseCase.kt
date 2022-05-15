package com.badger.familyorgfe.features.familyauthjourney.all.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import javax.inject.Inject

class AcceptInviteUseCase @Inject constructor() : BaseUseCase<Long, FamiliesAndInvites>() {

    override suspend fun invoke(arg: Long): FamiliesAndInvites {
        return FamiliesAndInvites.createEmpty()
    }
}
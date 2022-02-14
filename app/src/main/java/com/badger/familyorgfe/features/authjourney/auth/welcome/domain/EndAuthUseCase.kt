package com.badger.familyorgfe.features.authjourney.auth.welcome.domain

import com.badger.familyorgfe.base.SyncUseCase
import com.badger.familyorgfe.data.repository.TestRepo
import javax.inject.Inject

class EndAuthUseCase @Inject constructor(
) : SyncUseCase<Unit, Unit>() {

    override suspend fun invoke(arg: Unit) {
        TestRepo.isUserAuthorized.tryEmit(true)
    }
}
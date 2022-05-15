package com.badger.familyorgfe.features.familyauthjourney.all.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.Family
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import javax.inject.Inject
import kotlin.random.Random

class GetFamiliesUseCase @Inject constructor() : BaseUseCase<Unit, FamiliesAndInvites>() {

    override suspend fun invoke(arg: Unit): FamiliesAndInvites {
        return FamiliesAndInvites(
            families = listOf(
                createFamily().copy(name = "Тюлины"),
                createFamily().copy(name = "Яковлевы"),
            ),
            invites = listOf(
                createFamily().copy(name = "Таршины")
            )
        )
    }

    private fun createFamily() = Family(
        id = Random.nextLong(),
        name = "",
        members = emptyList(),
        invites = emptyList()
    )
}
package com.badger.familyorgfe.features.familyauthjourney.all.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.family.FamilyAuthApi
import com.badger.familyorgfe.data.source.family.GetAllJson
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import javax.inject.Inject

class GetFamiliesUseCase @Inject constructor(
    private val api: FamilyAuthApi
) : BaseUseCase<Unit, FamiliesAndInvites>() {

    override suspend fun invoke(arg: Unit): FamiliesAndInvites {
        val result = try {
            val response = api.getAll(GetAllJson.Form())
            FamiliesAndInvites(
                families = response.families,
                invites = response.invites
            )
        } catch (e: Exception) {
            FamiliesAndInvites.createEmpty()
        }
        return result
    }
}
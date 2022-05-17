package com.badger.familyorgfe.features.familyauthjourney.all.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.family.AcceptInviteJson
import com.badger.familyorgfe.data.source.family.FamilyAuthApi
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import javax.inject.Inject

class AcceptInviteUseCase @Inject constructor(
    private val familyAuthApi: FamilyAuthApi
) : BaseUseCase<AcceptInviteUseCase.Arg, FamiliesAndInvites>() {

    data class Arg(
        val familyId: Long,
        val familiesAndInvites: FamiliesAndInvites
    )

    override suspend fun invoke(arg: Arg): FamiliesAndInvites {
        return try {
            val form = AcceptInviteJson.Form(arg.familyId)
            val response = familyAuthApi.acceptInvite(form)

            if (response.success && response.family != null) {
                FamiliesAndInvites(
                    families = arg.familiesAndInvites.families + response.family,
                    invites = arg.familiesAndInvites.invites.filter { it.id != arg.familyId }
                )
            } else {
                arg.familiesAndInvites
            }
        } catch (e: Exception) {
            arg.familiesAndInvites
        }
    }
}
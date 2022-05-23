package com.badger.familyorgfe.features.familyauthjourney.all.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.source.familyauth.FamilyAuthApi
import com.badger.familyorgfe.data.source.familyauth.json.LeaveJson
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import javax.inject.Inject

class DeclineInviteUseCase @Inject constructor(
    private val familyAuthApi: FamilyAuthApi
) : BaseUseCase<DeclineInviteUseCase.Arg, FamiliesAndInvites>() {

    data class Arg(
        val familyId: Long,
        val familiesAndInvites: FamiliesAndInvites
    )

    override suspend fun invoke(arg: Arg): FamiliesAndInvites {
        return try {
            val form = LeaveJson.Form(arg.familyId)
            val response = familyAuthApi.leave(form)

            if (response.success) {
                FamiliesAndInvites(
                    families = arg.familiesAndInvites.families.filter { it.id != arg.familyId },
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
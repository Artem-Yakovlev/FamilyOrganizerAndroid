package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.base.ResponseError
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.family.FamilyApi
import com.badger.familyorgfe.data.source.family.json.InviteFamilyMemberJson
import com.badger.familyorgfe.features.appjourney.profile.IProfileViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class InviteFamilyMemberUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val familyApi: FamilyApi
) : BaseUseCase<String, IProfileViewModel.ErrorType?>() {

    override suspend fun invoke(arg: String): IProfileViewModel.ErrorType? {
        return dataStoreRepository.familyId.firstOrNull()?.let { familyId ->
            val form = InviteFamilyMemberJson.Form(familyId = familyId, memberEmail = arg)

            try {
                val result = familyApi.inviteFamilyMember(form)
                if (result.hasNoErrors() && result.data.success) {
                    null
                } else {
                    when (result.error) {
                        ResponseError.NONE ->
                            null
                        ResponseError.FAMILY_MEMBER_DOES_NOT_EXIST ->
                            IProfileViewModel.ErrorType.USER_DOES_NOT_EXIST
                        else ->
                            IProfileViewModel.ErrorType.UNEXPECTED
                    }
                }
            } catch (e: Exception) {
                IProfileViewModel.ErrorType.UNEXPECTED
            }

        } ?: IProfileViewModel.ErrorType.UNEXPECTED
    }
}
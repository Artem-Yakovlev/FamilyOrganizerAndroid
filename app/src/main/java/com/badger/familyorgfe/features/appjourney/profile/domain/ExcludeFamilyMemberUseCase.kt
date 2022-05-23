package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.family.json.ExcludeFamilyMemberJson
import com.badger.familyorgfe.data.source.family.FamilyApi
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ExcludeFamilyMemberUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val familyApi: FamilyApi
) : BaseUseCase<FamilyMember, Unit>() {

    override suspend fun invoke(arg: FamilyMember) {
        dataStoreRepository.familyId.firstOrNull()
            ?.let { familyId ->
                val form = ExcludeFamilyMemberJson.Form(
                    familyId = familyId,
                    memberEmail = arg.email
                )
                try {
                    familyApi.excludeFamilyMember(form)
                } catch (e: Exception) {

                }
            }
    }

}
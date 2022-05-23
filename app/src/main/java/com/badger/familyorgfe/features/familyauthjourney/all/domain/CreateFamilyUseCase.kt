package com.badger.familyorgfe.features.familyauthjourney.all.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.familyauth.json.CreateJson
import com.badger.familyorgfe.data.source.familyauth.FamilyAuthApi
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import javax.inject.Inject

class CreateFamilyUseCase @Inject constructor(
    private val api: FamilyAuthApi,
    private val dataStore: IDataStoreRepository
) : BaseUseCase<String, FamiliesAndInvites>() {

    override suspend fun invoke(arg: String) : FamiliesAndInvites {
        val result = try {
            val response = api.create(CreateJson.Form(arg))
            dataStore.setFamilyId(response.createdId)
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
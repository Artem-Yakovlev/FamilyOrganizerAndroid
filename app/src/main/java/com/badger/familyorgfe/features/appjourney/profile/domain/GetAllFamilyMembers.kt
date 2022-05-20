package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseResponse
import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.repository.IUserRepository
import com.badger.familyorgfe.data.source.family.FamilyApi
import com.badger.familyorgfe.data.source.family.GetAllMembersJson
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetAllFamilyMembers @Inject constructor(
    private val familyApi: FamilyApi,
    private val userRepository: IUserRepository,
    private val dataStore: IDataStoreRepository
) : BaseUseCase<Unit, List<FamilyMember>>() {

    override suspend fun invoke(arg: Unit) = dataStore.familyId.firstOrNull()?.let { familyId ->
        val localNames = userRepository.getAllLocalNames()
        val currentUserEmail = dataStore.userEmail.firstOrNull()
        try {
            getOnlineUsers(familyId).map { onlineUser ->
                FamilyMember.createForOnlineUser(
                    name = localNames
                        .find { onlineUser.user.email == it.email }?.localName
                        ?: onlineUser.user.name,
                    onlineUser = onlineUser
                )
            }.filter { it.email != currentUserEmail }.sortedBy(FamilyMember::email)
        } catch (e: Exception) {
            emptyList()
        }
    } ?: emptyList()

    private suspend fun getOnlineUsers(familyId: Long) = familyApi
        .getAllMembers(form = GetAllMembersJson.Form(familyId))
        .takeIf(BaseResponse<GetAllMembersJson.Response>::hasNoErrors)?.data?.onlineUsers
        ?: emptyList()
}
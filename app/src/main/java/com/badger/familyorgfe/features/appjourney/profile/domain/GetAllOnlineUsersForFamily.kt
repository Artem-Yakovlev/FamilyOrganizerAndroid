package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseResponse
import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.family.FamilyApi
import com.badger.familyorgfe.data.source.family.json.GetAllMembersJson
import com.badger.familyorgfe.data.source.family.model.OnlineUser
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetAllOnlineUsersForFamily @Inject constructor(
    private val familyApi: FamilyApi,
    private val dataStore: IDataStoreRepository
) : BaseUseCase<Unit, List<OnlineUser>>() {

    override suspend fun invoke(arg: Unit): List<OnlineUser> {
        return dataStore.familyId.firstOrNull()?.let { familyId ->
            dataStore.userEmail.firstOrNull()?.let { userEmail ->
                try {
                    getOnlineUsers(familyId).filter { it.user.email != userEmail }
                } catch (e: Exception) {
                    emptyList()
                }
            }
        } ?: emptyList()
    }

    //    override fun invoke(arg: Unit): Flow<List<FamilyMember>> = combine(
//        dataStore.familyId.filterNotNull(),
//        dataStore.userEmail.filterNotNull()
//    ) { familyId, userEmail ->
//        combine(
//            userRepository.getAllLocalNames(),
//            flow {
//                val onlineUsers = try {
//                    getOnlineUsers(familyId)
//                } catch (e: Exception) {
//                    emptyList()
//                }
//                emit(onlineUsers)
//            }
//        ) { localNames, onlineUsers ->
//            onlineUsers.map { onlineUser ->
//                FamilyMember.createForOnlineUser(
//                    name = localNames
//                        .find { onlineUser.user.email == it.email }?.localName
//                        ?: onlineUser.user.name,
//                    onlineUser = onlineUser
//                )
//            }.filter { it.email != userEmail }.sortedBy(FamilyMember::email)
//        }
//    }.flatMapLatest { it }
//
    private suspend fun getOnlineUsers(familyId: Long) = familyApi
        .getAllMembers(form = GetAllMembersJson.Form(familyId))
        .takeIf(BaseResponse<GetAllMembersJson.Response>::hasNoErrors)?.data?.onlineUsers
        ?: emptyList()
}
package com.badger.familyorgfe.features.appjourney.profile.domain

import com.badger.familyorgfe.base.BaseResponse
import com.badger.familyorgfe.base.FlowUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.repository.IUserRepository
import com.badger.familyorgfe.data.source.family.FamilyApi
import com.badger.familyorgfe.data.source.family.GetAllMembersJson
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllFamilyMembersUseCase @Inject constructor(
    private val familyApi: FamilyApi,
    private val userRepository: IUserRepository,
    private val dataStore: IDataStoreRepository
) : FlowUseCase<Unit, List<FamilyMember>>() {

    override fun invoke(arg: Unit): Flow<List<FamilyMember>> = combine(
        dataStore.familyId.filterNotNull(),
        dataStore.userEmail.filterNotNull()
    ) { familyId, userEmail ->
        combine(
            userRepository.getAllLocalNames(),
            flow {
                val onlineUsers = try {
                    getOnlineUsers(familyId)
                } catch (e: Exception) {
                    emptyList()
                }
                emit(onlineUsers)
            }
        ) { localNames, onlineUsers ->
            onlineUsers.map { onlineUser ->
                FamilyMember.createForOnlineUser(
                    name = localNames
                        .find { onlineUser.user.email == it.email }?.localName
                        ?: onlineUser.user.name,
                    onlineUser = onlineUser
                )
            }.filter { it.email != userEmail }.sortedBy(FamilyMember::email)
        }
    }.flatMapLatest { it }

//    override fun invoke(arg: Unit) = dataStore.familyId.firstOrNull()?.let { familyId ->
//        val currentUserEmail = dataStore.userEmail.firstOrNull()
//        try {
//            combine(
//                userRepository.getAllLocalNames(),
//                flow { emit(getOnlineUsers(familyId)) }
//            ) { localNames, onlineUsers ->
//                onlineUsers.map { onlineUser ->
//                    FamilyMember.createForOnlineUser(
//                        name = localNames
//                            .find { onlineUser.user.email == it.email }?.localName
//                            ?: onlineUser.user.name,
//                        onlineUser = onlineUser
//                    )
//                }.filter { it.email != currentUserEmail }.sortedBy(FamilyMember::email)
//            }
//
////            getOnlineUsers(familyId).map { onlineUser ->
////                FamilyMember.createForOnlineUser(
////                    name = localNames
////                        .find { onlineUser.user.email == it.email }?.localName
////                        ?: onlineUser.user.name,
////                    onlineUser = onlineUser
////                )
////            }.filter { it.email != currentUserEmail }.sortedBy(FamilyMember::email)
//        } catch (e: Exception) {
//            emptyList()
//        }
//    } ?: emptyList()

    private suspend fun getOnlineUsers(familyId: Long) = familyApi
        .getAllMembers(form = GetAllMembersJson.Form(familyId))
        .takeIf(BaseResponse<GetAllMembersJson.Response>::hasNoErrors)?.data?.onlineUsers
        ?: emptyList()
}
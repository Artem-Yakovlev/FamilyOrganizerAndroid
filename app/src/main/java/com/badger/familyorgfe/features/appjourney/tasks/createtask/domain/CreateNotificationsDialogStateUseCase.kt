package com.badger.familyorgfe.features.appjourney.tasks.createtask.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.family.FamilyApi
import com.badger.familyorgfe.data.source.family.json.GetAllMembersJson
import com.badger.familyorgfe.data.source.family.model.OnlineUser
import com.badger.familyorgfe.features.appjourney.profile.domain.GetAllLocalNamesUseCase
import com.badger.familyorgfe.features.appjourney.tasks.createtask.ICreateTaskViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateNotificationsDialogStateUseCase @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val getAllLocalNamesUseCase: GetAllLocalNamesUseCase,
    private val familyApi: FamilyApi
) : BaseUseCase<List<String>, ICreateTaskViewModel.NotificationDialogState>() {

    override suspend fun invoke(arg: List<String>): ICreateTaskViewModel.NotificationDialogState {
        return dataStoreRepository.familyId.first()?.let { familyId ->
            val allMembers = getAllMembers(familyId)
            val localNames = getAllLocalNamesUseCase(Unit).first()

            ICreateTaskViewModel.NotificationDialogState.create(
                notifications = arg,
                allUsers = allMembers,
                names = localNames
            )
        } ?: ICreateTaskViewModel.NotificationDialogState.createEmpty()
    }

    private suspend fun getAllMembers(familyId: Long): List<String> {
        val form = GetAllMembersJson.Form(familyId = familyId)
        return try {
            familyApi.getAllMembers(form)
                .data.onlineUsers
                .map(OnlineUser::user)
                .map(User::email)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
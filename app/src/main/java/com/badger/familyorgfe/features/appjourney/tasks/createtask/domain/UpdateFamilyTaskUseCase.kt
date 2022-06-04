package com.badger.familyorgfe.features.appjourney.tasks.createtask.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.tasks.TasksApi
import com.badger.familyorgfe.data.source.tasks.json.ModifyJson
import com.badger.familyorgfe.data.source.tasks.model.RemoteTask.Companion.toRemote
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UpdateFamilyTaskUseCase @Inject constructor(
    private val tasksApi: TasksApi,
    private val dataStoreRepository: IDataStoreRepository
) : BaseUseCase<FamilyTask, Boolean>() {

    override suspend fun invoke(arg: FamilyTask): Boolean {
        return dataStoreRepository.familyId.firstOrNull()?.let { familyId ->
            val form = ModifyJson.Form(
                familyId = familyId,
                task = arg.toRemote()
            )
            try {
                tasksApi.modify(form).hasNoErrors()
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } ?: false
    }
}
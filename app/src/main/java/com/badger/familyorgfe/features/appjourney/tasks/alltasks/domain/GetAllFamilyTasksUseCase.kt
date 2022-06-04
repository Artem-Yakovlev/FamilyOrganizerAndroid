package com.badger.familyorgfe.features.appjourney.tasks.alltasks.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.tasks.TasksApi
import com.badger.familyorgfe.data.source.tasks.json.GetAllJson
import com.badger.familyorgfe.data.source.tasks.model.RemoteTask
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetAllFamilyTasksUseCase @Inject constructor(
    private val tasksApi: TasksApi,
    private val dataStoreRepository: IDataStoreRepository
) : BaseUseCase<Unit, List<FamilyTask>>() {

    override suspend fun invoke(arg: Unit): List<FamilyTask> {
        return dataStoreRepository.familyId.firstOrNull()?.let { familyId ->
            val form = GetAllJson.Form(familyId = familyId)
            try {
                val result = tasksApi.getAll(form)
                result.data.tasks.map(RemoteTask::toFamilyTask)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        } ?: emptyList()
    }
}
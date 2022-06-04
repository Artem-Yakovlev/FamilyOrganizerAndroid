package com.badger.familyorgfe.features.appjourney.tasks.taskdetails.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.data.source.tasks.TasksApi
import com.badger.familyorgfe.data.source.tasks.json.DeleteJson
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val tasksApi: TasksApi,
    private val dataStoreRepository: IDataStoreRepository
) : BaseUseCase<Long, Unit>() {

    override suspend fun invoke(arg: Long) {
        dataStoreRepository.familyId.firstOrNull()?.let { familyId ->
            val form = DeleteJson.Form(familyId = familyId, taskId = arg)
            try {
                tasksApi.delete(form)
            } catch (e: Exception) {

            }
        }
    }
}
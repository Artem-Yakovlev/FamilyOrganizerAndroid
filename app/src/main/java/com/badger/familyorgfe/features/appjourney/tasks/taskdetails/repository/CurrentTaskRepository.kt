package com.badger.familyorgfe.features.appjourney.tasks.taskdetails.repository

import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository.IAllTasksRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CurrentTaskRepository @Inject constructor(
    private val allTasksRepository: IAllTasksRepository
) : ICurrentTaskRepository {

    private var currentTaskId: Long? = null

    override suspend fun getCurrentTask(): FamilyTask? {
        return (allTasksRepository.closedTasks.firstOrNull().orEmpty() +
                allTasksRepository.openTasks.firstOrNull()
                    .orEmpty()).find { task -> task.id == currentTaskId }
    }

    override suspend fun setFamilyTaskId(familyTaskId: Long?) {
        currentTaskId = familyTaskId
    }
}
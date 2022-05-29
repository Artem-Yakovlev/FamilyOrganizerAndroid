package com.badger.familyorgfe.features.appjourney.tasks.taskdetails.repository

import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository.IAllTasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class CurrentTaskRepository(
    allTasksRepository: IAllTasksRepository
) : ICurrentTaskRepository {

    private val currentTaskId = MutableStateFlow<Long?>(null)

    override val currentTask: Flow<FamilyTask?> = combine(
        allTasksRepository.openTasks, allTasksRepository.closedTasks
    ) { openTasks, closedTasks ->
        openTasks + closedTasks
    }.combine(currentTaskId) { tasks, taskId ->
        tasks.find { it.id == taskId }
    }

    override suspend fun setFamilyTaskId(familyTaskId: Long?) {
        currentTaskId.value = familyTaskId
    }
}
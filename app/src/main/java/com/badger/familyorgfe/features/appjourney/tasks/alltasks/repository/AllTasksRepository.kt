package com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository

import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.TaskCategory
import com.badger.familyorgfe.data.model.TaskStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AllTasksRepository @Inject constructor() : IAllTasksRepository {

    override val currentCategory = MutableStateFlow<TaskCategory>(TaskCategory.All)

    private val _openTasks = MutableStateFlow<List<FamilyTask>>(emptyList())
    override val openTasks = combine(currentCategory, _openTasks, ::filterTasksByCategory)

    private val _closedTasks = MutableStateFlow<List<FamilyTask>>(emptyList())
    override val closedTasks = combine(currentCategory, _closedTasks, ::filterTasksByCategory)

    override suspend fun updateData() {
        _openTasks.value = listOf(
            FamilyTask.createMock(),
            FamilyTask.createMock(),
            FamilyTask.createMock()
        ).map { it.copy(status = TaskStatus.ACTIVE) }

        _closedTasks.value = listOf(
            FamilyTask.createMock(),
            FamilyTask.createMock()
        ).map {
            it.copy(
                status = listOf(
                    TaskStatus.FINISHED,
                    TaskStatus.FAILED
                ).random()
            )
        }
    }

    override suspend fun changeCategory(category: TaskCategory) {
        currentCategory.value = category
    }

    private fun filterTasksByCategory(
        category: TaskCategory,
        tasks: List<FamilyTask>
    ): List<FamilyTask> {
        return if (category != TaskCategory.All) {
            tasks.filter { task -> task.category == category }
        } else {
            tasks
        }
    }
}
package com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository

import com.badger.familyorgfe.data.model.FamilyTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AllTasksRepository @Inject constructor() : IAllTasksRepository {

    override val currentCategory = MutableStateFlow(FamilyTask.Category.ALL_CATEGORY)

    private val _openTasks = MutableStateFlow<List<FamilyTask>>(emptyList())
    override val openTasks = combine(currentCategory, _openTasks, ::filterTasksByCategory)

    private val _closedTasks = MutableStateFlow<List<FamilyTask>>(emptyList())
    override val closedTasks = combine(currentCategory, _closedTasks, ::filterTasksByCategory)

    override suspend fun updateData() {
        _openTasks.value = listOf(
            FamilyTask.createMock(),
            FamilyTask.createMock(),
            FamilyTask.createMock()
        ).map { it.copy(status = FamilyTask.Status.ACTIVE) }

        _closedTasks.value = listOf(
            FamilyTask.createMock(),
            FamilyTask.createMock()
        ).map {
            it.copy(
                status = listOf(
                    FamilyTask.Status.FINISHED,
                    FamilyTask.Status.FAILED
                ).random()
            )
        }
    }

    override suspend fun changeCategory(category: FamilyTask.Category) {
        currentCategory.value = category
    }

    private fun filterTasksByCategory(
        category: FamilyTask.Category,
        tasks: List<FamilyTask>
    ): List<FamilyTask> {
        return if (category != FamilyTask.Category.ALL_CATEGORY) {
            tasks.filter { task -> task.category == category }
        } else {
            tasks
        }
    }
}
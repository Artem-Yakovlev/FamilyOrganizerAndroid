package com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository

import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.Subtask
import com.badger.familyorgfe.data.model.TaskCategory
import com.badger.familyorgfe.data.model.TaskProduct
import com.badger.familyorgfe.features.appjourney.tasks.alltasks.domain.GetAllFamilyTasksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AllTasksRepository @Inject constructor(
    private val getAllFamilyTasksUseCase: GetAllFamilyTasksUseCase
) : IAllTasksRepository {

    override val currentCategory = MutableStateFlow<TaskCategory>(TaskCategory.All)

    private val _openTasks = MutableStateFlow<List<FamilyTask>>(emptyList())
    override val openTasks = combine(currentCategory, _openTasks, ::filterTasksByCategory)

    private val _closedTasks = MutableStateFlow<List<FamilyTask>>(emptyList())
    override val closedTasks = combine(currentCategory, _closedTasks, ::filterTasksByCategory)

    private suspend fun getAllTasks(): List<FamilyTask> {
        return withContext(Dispatchers.Default) {
            getAllFamilyTasksUseCase(Unit).map {
                it.copy(
                    products = it.products.sortedBy(TaskProduct::title),
                    subtasks = it.subtasks.sortedBy(Subtask::text)
                )
            }
        }
    }

    override suspend fun updateData() {
        val allTasks = getAllTasks()
        _openTasks.value = allTasks.filter(FamilyTask::isActive)
        _closedTasks.value = allTasks.filterNot(FamilyTask::isActive)
    }

    override suspend fun changeCategory(category: TaskCategory) {
        currentCategory.value = category
    }

    private fun filterTasksByCategory(
        category: TaskCategory,
        tasks: List<FamilyTask>
    ): List<FamilyTask> {
        return if (category != TaskCategory.All) {
            tasks.filter { task -> task.category.ordinal == category.ordinal }
        } else {
            tasks
        }
    }
}
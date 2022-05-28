package com.badger.familyorgfe.features.appjourney.tasks.alltasks.repository

import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.TaskCategory
import kotlinx.coroutines.flow.Flow

interface IAllTasksRepository {

    val currentCategory: Flow<TaskCategory>

    val openTasks: Flow<List<FamilyTask>>

    val closedTasks: Flow<List<FamilyTask>>

    suspend fun updateData()

    suspend fun changeCategory(category: TaskCategory)
}
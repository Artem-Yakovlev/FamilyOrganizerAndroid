package com.badger.familyorgfe.features.appjourney.tasks.taskdetails.repository

import com.badger.familyorgfe.data.model.FamilyTask
import kotlinx.coroutines.flow.Flow

interface ICurrentTaskRepository {

    val currentTask: Flow<FamilyTask?>

    suspend fun setFamilyTaskId(familyTaskId: Long?)
}
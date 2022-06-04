package com.badger.familyorgfe.features.appjourney.tasks.taskdetails.repository

import com.badger.familyorgfe.data.model.FamilyTask

interface ICurrentTaskRepository {

    suspend fun getCurrentTask() : FamilyTask?

    suspend fun setFamilyTaskId(familyTaskId: Long?)
}
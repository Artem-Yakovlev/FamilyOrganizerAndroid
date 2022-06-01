package com.badger.familyorgfe.features.appjourney.tasks.createtask.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.FamilyTask
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetFamilyTaskByIdUseCase @Inject constructor() : BaseUseCase<Long, FamilyTask?>() {

    override suspend fun invoke(arg: Long): FamilyTask? {
        delay(5000)
        return FamilyTask.createMock()
    }
}
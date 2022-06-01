package com.badger.familyorgfe.features.appjourney.tasks.createtask.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.FamilyTask
import kotlinx.coroutines.delay
import javax.inject.Inject

class CreateFamilyTaskUseCase @Inject constructor(

) : BaseUseCase<FamilyTask, Boolean>() {

    override suspend fun invoke(arg: FamilyTask): Boolean {
        delay(5000)
        return true
    }
}
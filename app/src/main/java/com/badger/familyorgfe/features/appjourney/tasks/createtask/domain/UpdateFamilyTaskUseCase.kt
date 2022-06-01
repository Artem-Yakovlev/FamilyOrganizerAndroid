package com.badger.familyorgfe.features.appjourney.tasks.createtask.domain

import com.badger.familyorgfe.base.BaseUseCase
import com.badger.familyorgfe.data.model.FamilyTask
import kotlinx.coroutines.delay
import javax.inject.Inject

class UpdateFamilyTaskUseCase @Inject constructor(

) : BaseUseCase<FamilyTask, Boolean>() {

    override suspend fun invoke(arg: FamilyTask): Boolean {
        delay(2500)
        return true
    }
}
package com.badger.familyorgfe.features.appjourney.products.adding.model

import com.badger.familyorgfe.data.model.FamilyTask

data class UpdatableTask(
    val taskId: Long,
    val title: String,
    val checked: Boolean
) {
    companion object {
        fun fromFamilyTask(familyTask: FamilyTask) = UpdatableTask(
            taskId = familyTask.id,
            title = familyTask.title,
            checked = false
        )
    }
}
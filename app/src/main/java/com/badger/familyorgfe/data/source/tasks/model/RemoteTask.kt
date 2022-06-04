package com.badger.familyorgfe.data.source.tasks.model

import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.Subtask
import com.badger.familyorgfe.data.model.TaskProduct
import com.badger.familyorgfe.data.model.TaskStatus

data class RemoteTask(
    val id: Long,
    val category: RemoteCategory,
    val status: TaskStatus,
    val title: String,
    val description: String,
    val notifications: List<String>,
    val products: List<TaskProduct>,
    val subtasks: List<Subtask>
) {
    fun toFamilyTask(): FamilyTask {
        return FamilyTask(
            id = id,
            category = category.toCategory(),
            status = status,
            title = title,
            desc = description,
            notificationEmails = notifications,
            products = products,
            subtasks = subtasks
        )
    }
}
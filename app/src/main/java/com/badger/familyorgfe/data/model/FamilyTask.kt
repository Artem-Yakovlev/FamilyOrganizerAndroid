package com.badger.familyorgfe.data.model

import org.threeten.bp.LocalDateTime

data class FamilyTask(
    val id: String,

    val type: TaskType,
    val status: TaskStatus,

    val title: String,
    val desc: String,

    val localDateTime: LocalDateTime?,
    val hasProducts: Boolean
)

enum class TaskStatus {
    ACTIVE,
    FINISHED,
    FAILED
}

enum class TaskType {
    ONE_SHOT,
    ONE_TIME,
    RECURRING
}
package com.badger.familyorgfe.data.model

import org.threeten.bp.LocalDateTime
import kotlin.random.Random

data class FamilyTask(
    val id: Long,

    val type: TaskType,
    val status: TaskStatus,

    val title: String,
    val desc: String,

    val localDateTime: LocalDateTime?,
    val hasProducts: Boolean
) {

    companion object {
        fun createMock() = FamilyTask(
            id = Random.nextLong(),
            type = TaskType.values().random(),
            status = TaskStatus.values().random(),
            title = "Заголовок",
            desc = "Описание",
            localDateTime = LocalDateTime.now(),
            hasProducts = true
        )
    }
}

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
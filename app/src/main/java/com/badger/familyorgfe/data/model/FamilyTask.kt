package com.badger.familyorgfe.data.model

import com.badger.familyorgfe.R
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import kotlin.random.Random

data class FamilyTask(
    val id: Long,

    val category: TaskCategory,
    val status: TaskStatus,

    val title: String,
    val desc: String,

    val notificationEmails: List<String>,
    val products: List<TaskProduct>
) {

    val hasNotifications get() = notificationEmails.isNotEmpty()

    val hasProducts get() = products.isNotEmpty()

    val previewLocalDateTime = when (category) {
        is TaskCategory.All -> null
        is TaskCategory.OneShot -> null
        is TaskCategory.OneTime -> category.localDateTime
        is TaskCategory.Recurring.DaysOfWeek -> LocalDateTime.now()
        is TaskCategory.Recurring.EveryNDays -> LocalDateTime.now()
        is TaskCategory.Recurring.EveryYear -> LocalDateTime.now()
    }

    companion object {
        fun createMock() = FamilyTask(
            id = Random.nextLong(),
            category = listOf(
                TaskCategory.OneShot,
                TaskCategory.OneTime(LocalDateTime.now(), false)
            ).random(),
            status = TaskStatus.values().random(),
            title = "Заголовок",
            desc = "Описание",
            notificationEmails = emptyList(),
            products = emptyList()
        )
    }
}

sealed class TaskCategory {

    abstract val resourceId: Int
    abstract val isTimeImportant: Boolean

    object All : TaskCategory() {
        override val resourceId get() = R.drawable.ic_all_categories
        override val isTimeImportant = false
    }

    object OneShot : TaskCategory() {
        override val resourceId get() = R.drawable.ic_one_shot
        override val isTimeImportant = false
    }

    data class OneTime(
        val localDateTime: LocalDateTime,
        override val isTimeImportant: Boolean
    ) : TaskCategory() {
        override val resourceId: Int = R.drawable.ic_one_time

        companion object {
            val mock = OneTime(
                localDateTime = LocalDateTime.now(),
                isTimeImportant = false
            )
        }
    }

    sealed class Recurring : TaskCategory() {
        override val resourceId: Int = R.drawable.ic_recurring

        data class DaysOfWeek(
            val days: List<DayOfWeek>,
            override val isTimeImportant: Boolean
        ) : Recurring()

        data class EveryNDays(
            val localDateTime: LocalDateTime,
            val nDays: Int,
            override val isTimeImportant: Boolean
        ) : Recurring()

        data class EveryYear(
            val localDateTime: LocalDateTime,
            override val isTimeImportant: Boolean
        ) : Recurring()

        companion object {
            val mock = DaysOfWeek(
                days = emptyList(),
                isTimeImportant = false
            )
        }
    }

    companion object {
        fun getAllCategories() = listOf(
            All, OneShot, OneTime.mock, Recurring.mock
        )
    }
}

enum class TaskStatus(val textResourceId: Int) {
    ACTIVE(textResourceId = R.string.all_tasks_active_status_text),
    FINISHED(textResourceId = R.string.all_tasks_finished_status_text),
    FAILED(textResourceId = R.string.all_tasks_failed_status_text)
}

data class TaskProduct(
    val title: String,
    val amount: Double,
    val measure: Product.Measure
)



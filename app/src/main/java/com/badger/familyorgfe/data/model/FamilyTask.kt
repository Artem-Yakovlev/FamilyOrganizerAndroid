package com.badger.familyorgfe.data.model

import com.badger.familyorgfe.R
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import kotlin.random.Random

data class FamilyTask(
    val id: Long,

    val category: TaskCategory,
    val status: TaskStatus,

    val title: String,
    val desc: String,

    val notificationEmails: List<String>,
    val products: List<TaskProduct>,
    val subtasks: List<Subtask>
) {

    val hasNotifications get() = notificationEmails.isNotEmpty()

    val hasProducts get() = products.isNotEmpty()

    val previewLocalDateTime = when (category) {
        is TaskCategory.All -> null
        is TaskCategory.OneShot -> null
        is TaskCategory.OneTime -> category.localDateTime
        is TaskCategory.Recurring.DaysOfWeek -> {
            var nextDayOfWeek = category.days.first().value
            val currentDayOfWeek = LocalDate.now().dayOfWeek.value

            for (day in category.days.map(DayOfWeek::getValue)) {
                if (currentDayOfWeek <= day) {
                    if (category.isTimeImportant && currentDayOfWeek == day) {
                        if (LocalTime.now() < category.time) {
                            nextDayOfWeek = day
                            break
                        }
                    } else {
                        nextDayOfWeek = day
                        break
                    }
                }
            }
            //TODO() Написать тесты
            LocalDateTime.now()
                .withHour(category.time.hour)
                .withMinute(category.time.minute)
                .plusDays(betweenDayOfWeeks(currentDayOfWeek, nextDayOfWeek).toLong())
        }
        is TaskCategory.Recurring.EveryNDays ->
            if (category.localDateTime.isBefore(LocalDateTime.now())) {
                category.localDateTime.plusDays(category.nDays.toLong())
            } else {
                category.localDateTime
            }
        is TaskCategory.Recurring.EveryYear ->
            if (category.localDateTime.isBefore(LocalDateTime.now())) {
                category.localDateTime.plusYears(1)
            } else {
                category.localDateTime
            }

    }

    private fun betweenDayOfWeeks(currentDayOfWeek: Int, dayOfWeek: Int): Int {
        return if (currentDayOfWeek <= dayOfWeek) {
            dayOfWeek - currentDayOfWeek
        } else {
            dayOfWeek + 7 - currentDayOfWeek
        }
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
            products = emptyList(),
            subtasks = emptyList()
        )
    }
}

sealed class TaskCategory {

    abstract val imageResId: Int
    abstract val isTimeImportant: Boolean

    object All : TaskCategory() {
        override val imageResId get() = R.drawable.ic_all_categories
        override val isTimeImportant = false
    }

    object OneShot : TaskCategory() {
        override val imageResId get() = R.drawable.ic_one_shot
        override val isTimeImportant = false
    }

    data class OneTime(
        val localDateTime: LocalDateTime,
        override val isTimeImportant: Boolean
    ) : TaskCategory() {
        override val imageResId: Int = R.drawable.ic_one_time

        companion object {
            val mock = OneTime(
                localDateTime = LocalDateTime.now(),
                isTimeImportant = false
            )
        }
    }

    sealed class Recurring : TaskCategory() {
        override val imageResId: Int = R.drawable.ic_recurring

        data class DaysOfWeek(
            val days: List<DayOfWeek>,
            val time: LocalTime,
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
                time = LocalTime.now(),
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

data class Subtask(
    val id: Long,
    val text: String,
    val checked: Boolean
)



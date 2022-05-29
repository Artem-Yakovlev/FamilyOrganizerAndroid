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
                TaskCategory.OneTime(
                    localDateTime = LocalDateTime.now(),
                    isTimeImportant = false
                ),
                TaskCategory.OneTime(
                    localDateTime = LocalDateTime.now(),
                    isTimeImportant = true
                ),
                TaskCategory.Recurring.EveryYear(
                    localDateTime = LocalDateTime.now(),
                    isTimeImportant = true
                ),
                TaskCategory.Recurring.EveryYear(
                    localDateTime = LocalDateTime.now(),
                    isTimeImportant = false
                ),
                TaskCategory.Recurring.DaysOfWeek(
                    days = listOf(
                        DayOfWeek.MONDAY,
                        DayOfWeek.THURSDAY
                    ),
                    time = LocalTime.now(),
                    isTimeImportant = true
                ),
                TaskCategory.Recurring.DaysOfWeek(
                    days = listOf(
                        DayOfWeek.MONDAY,
                        DayOfWeek.THURSDAY
                    ),
                    time = LocalTime.now(),
                    isTimeImportant = false
                )
            ).random(),
            status = TaskStatus.values().random(),
            title = "Заголовок",
            desc = "Описание",
            notificationEmails = listOf("gleb_you@gmail.com", "maria_you@gmail.com"),
            products = emptyList(),
            subtasks = emptyList()
        )
    }
}

sealed class TaskCategory {

    abstract val imageResId: Int
    abstract val isTimeImportant: Boolean
    abstract val ordinal: Int

    object All : TaskCategory() {
        override val imageResId get() = R.drawable.ic_all_categories
        override val isTimeImportant = false
        override val ordinal: Int = 0
    }

    object OneShot : TaskCategory() {
        override val imageResId get() = R.drawable.ic_one_shot
        override val isTimeImportant = false
        override val ordinal: Int = 1
    }

    data class OneTime(
        val localDateTime: LocalDateTime,
        override val isTimeImportant: Boolean
    ) : TaskCategory() {
        override val imageResId: Int = R.drawable.ic_one_time
        override val ordinal: Int = 2

        companion object {
            val mock = OneTime(
                localDateTime = LocalDateTime.now(),
                isTimeImportant = false
            )
        }
    }

    sealed class Recurring : TaskCategory() {
        override val imageResId: Int = R.drawable.ic_recurring
        override val ordinal: Int = 3

        data class DaysOfWeek(
            val days: List<DayOfWeek>,
            val time: LocalTime,
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
    val id: Long,
    val checked: Boolean,
    val title: String,
    val amount: Double,
    val measure: Product.Measure
)

data class Subtask(
    val id: Long,
    val text: String,
    val checked: Boolean
)



package com.badger.familyorgfe.data.source.tasks.model

import com.badger.familyorgfe.data.model.TaskCategory
import com.badger.familyorgfe.ext.toInstantAtZone
import org.threeten.bp.DayOfWeek

sealed class RemoteCategory {
    abstract val isTimeImportant: Boolean

    object OneShot : RemoteCategory() {
        override val isTimeImportant = false
    }

    data class OneTime(
        val localDateTime: Long,
        override val isTimeImportant: Boolean
    ) : RemoteCategory()

    sealed class Recurring : RemoteCategory() {

        data class DaysOfWeek(
            val days: List<Int>,
            val time: Long,
            override val isTimeImportant: Boolean
        ) : Recurring()

        data class EveryYear(
            val localDateTime: Long,
            override val isTimeImportant: Boolean
        ) : Recurring()
    }

    fun toTaskCategory(): TaskCategory {
        return when (this) {
            is OneShot -> TaskCategory.OneShot
            is OneTime -> TaskCategory.OneTime(
                localDateTime = localDateTime.toInstantAtZone().toLocalDateTime(),
                isTimeImportant = isTimeImportant
            )
            is Recurring.DaysOfWeek -> TaskCategory.Recurring.DaysOfWeek(
                days = days.map { dayNumber -> DayOfWeek.of(dayNumber) },
                time = time.toInstantAtZone().toLocalTime(),
                isTimeImportant = isTimeImportant
            )
            is Recurring.EveryYear -> TaskCategory.Recurring.EveryYear(
                localDateTime = localDateTime.toInstantAtZone().toLocalDateTime(),
                isTimeImportant = isTimeImportant
            )
        }
    }
}
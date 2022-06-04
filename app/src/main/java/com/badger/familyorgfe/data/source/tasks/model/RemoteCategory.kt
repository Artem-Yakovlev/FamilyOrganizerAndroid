package com.badger.familyorgfe.data.source.tasks.model

import com.badger.familyorgfe.data.model.TaskCategory
import com.badger.familyorgfe.ext.toInstantAtZone
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId

class RemoteCategory(
    private val type: RemoteCategoryType,
    private val isTimeImportant: Boolean = false,
    private val dateTime: Long? = null,
    private val time: Long? = null,
    private val days: List<Int>? = null,
) {
    fun toCategory() = when (type) {
        RemoteCategoryType.ONE_SHOT -> TaskCategory.OneShot
        RemoteCategoryType.ONE_TIME -> TaskCategory.OneTime(
            localDateTime = dateTime?.toInstantAtZone()?.toLocalDateTime() ?: LocalDateTime.now(),
            isTimeImportant = isTimeImportant
        )
        RemoteCategoryType.DAYS_OF_WEEK -> TaskCategory.Recurring.DaysOfWeek(
            days = days?.map { dayNumber -> DayOfWeek.of(dayNumber) }.orEmpty(),
            time = time?.toInstantAtZone()?.toLocalTime() ?: LocalTime.now(),
            isTimeImportant = isTimeImportant
        )
        RemoteCategoryType.EVERY_YEAR -> TaskCategory.Recurring.EveryYear(
            localDateTime = dateTime?.toInstantAtZone()?.toLocalDateTime() ?: LocalDateTime.now(),
            isTimeImportant = isTimeImportant
        )
    }

    companion object {
        fun TaskCategory.toRemote() = when (this) {
            is TaskCategory.All, TaskCategory.OneShot ->
                RemoteCategory(type = RemoteCategoryType.ONE_SHOT)
            is TaskCategory.OneTime ->
                RemoteCategory(
                    type = RemoteCategoryType.ONE_SHOT,
                    dateTime = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond(),
                    isTimeImportant = isTimeImportant
                )
            is TaskCategory.Recurring.DaysOfWeek ->
                RemoteCategory(
                    type = RemoteCategoryType.DAYS_OF_WEEK,
                    days = days.map(DayOfWeek::getValue),
                    time = time.nano.toLong(),
                    isTimeImportant = isTimeImportant
                )
            is TaskCategory.Recurring.EveryYear ->
                RemoteCategory(
                    type = RemoteCategoryType.EVERY_YEAR,
                    dateTime = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond(),
                    isTimeImportant = isTimeImportant
                )
        }
    }
}

enum class RemoteCategoryType {
    ONE_SHOT,
    ONE_TIME,
    DAYS_OF_WEEK,
    EVERY_YEAR
}
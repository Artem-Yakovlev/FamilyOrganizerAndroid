package com.badger.familyorgfe.data.source.tasks.model

import com.badger.familyorgfe.data.model.TaskCategory
import com.badger.familyorgfe.ext.toInstantAtZone
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

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
}

enum class RemoteCategoryType {
    ONE_SHOT,
    ONE_TIME,
    DAYS_OF_WEEK,
    EVERY_YEAR
}

//sealed class RemoteCategory {
//    abstract val isTimeImportant: Boolean
//
//    object OneShot : RemoteCategory() {
//        override val isTimeImportant = false
//    }
//
//    data class OneTime(
//        val localDateTime: Long,
//        override val isTimeImportant: Boolean
//    ) : RemoteCategory()
//
//    sealed class Recurring : RemoteCategory() {
//
//        data class DaysOfWeek(
//            val days: List<Int>,
//            val time: Long,
//            override val isTimeImportant: Boolean
//        ) : Recurring()
//
//        data class EveryYear(
//            val localDateTime: Long,
//            override val isTimeImportant: Boolean
//        ) : Recurring()
//    }
//
//    fun toTaskCategory(): TaskCategory {
//        return when (this) {
//            is OneShot -> TaskCategory.OneShot
//            is TaskCategory.OneTime -> TaskCategory.OneTime(
//                localDateTime = localDateTime.toInstantAtZone().toLocalDateTime(),
//                isTimeImportant = isTimeImportant
//            )
//            is Recurring.DaysOfWeek -> TaskCategory.Recurring.DaysOfWeek(
//                days = days.map { dayNumber -> DayOfWeek.of(dayNumber) },
//                time = time.toInstantAtZone().toLocalTime(),
//                isTimeImportant = isTimeImportant
//            )
//            is Recurring.EveryYear -> TaskCategory.Recurring.EveryYear(
//                localDateTime = localDateTime.toInstantAtZone().toLocalDateTime(),
//                isTimeImportant = isTimeImportant
//            )
//        }
//    }
//}
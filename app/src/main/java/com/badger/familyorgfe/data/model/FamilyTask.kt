package com.badger.familyorgfe.data.model

import com.badger.familyorgfe.R
import org.threeten.bp.LocalDateTime
import kotlin.random.Random

data class FamilyTask(
    val id: Long,

    val category: Category,
    val status: Status,

    val title: String,
    val desc: String,

    val localDateTime: LocalDateTime?,
    val hasProducts: Boolean
) {

    companion object {
        fun createMock() = FamilyTask(
            id = Random.nextLong(),
            category = Category.values().random(),
            status = Status.values().random(),
            title = "Заголовок",
            desc = "Описание",
            localDateTime = LocalDateTime.now(),
            hasProducts = Random.nextBoolean()
        )
    }

    enum class Category(val resourceId: Int) {
        ALL_CATEGORY(resourceId = R.drawable.ic_all_categories),
        ONE_SHOT(resourceId = R.drawable.ic_one_shot),
        RECURRING(resourceId = R.drawable.ic_recurring),
        ONE_TIME(resourceId = R.drawable.ic_one_time)
    }

    enum class Status(val textResourceId: Int) {
        ACTIVE(textResourceId = R.string.all_tasks_active_status_text),
        FINISHED(textResourceId = R.string.all_tasks_finished_status_text),
        FAILED(textResourceId = R.string.all_tasks_failed_status_text)
    }
}



package com.badger.familyorgfe.features.appjourney.tasks.createtask

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.*
import com.badger.familyorgfe.ext.*
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

interface ICreateTaskViewModel : IBaseViewModel<ICreateTaskViewModel.Event> {

    val state: StateFlow<State>

    val loading: StateFlow<Boolean>

    val categoriesDialogState: StateFlow<CategoriesDialogState?>

    val notificationsDialogState: StateFlow<NotificationDialogState?>

    val subtasksDialogState: StateFlow<SubtasksDialogState?>

    val productsDialogState: StateFlow<ProductDialogState?>

    val saved: SharedFlow<Boolean>

    sealed class Event {

        sealed class Ordinal : Event() {
            object Init : Ordinal()
            object OnDoneClicked : Ordinal()

            data class OnTitleValueChanged(val value: String) : Ordinal()
            data class OnDescriptionValueChanged(val value: String) : Ordinal()

            data class OpenCategories(val category: TaskCategory) : Ordinal()

            data class OpenNotifications(val notifications: List<String>) : Ordinal()

            data class OpenSubtasks(val subtasks: List<Subtask>) : Ordinal()

            data class OpenProducts(val products: List<TaskProduct>) : Ordinal()
        }

        sealed class Categories : Event() {
            object OnOneShotCategoryClicked : Categories()
            object OnOneTimeCategoryClicked : Categories()
            object OnDaysOfWeekCategoryClicked : Categories()
            object OnEveryYearCategoryClicked : Categories()

            object Dismiss : Categories()
            object DismissCreating : Categories()
        }

        sealed class CreatingOneTimeCategory : Categories() {
            data class OnDateChanged(val date: String) : CreatingOneTimeCategory()
            data class OnTimeChanged(val time: String) : CreatingOneTimeCategory()
            object Save : CreatingOneTimeCategory()
        }

        sealed class CreatingDaysOfWeekCategory : Categories() {
            data class Add(val dayOfWeek: DayOfWeek) : CreatingDaysOfWeekCategory()
            data class Remove(val dayOfWeek: DayOfWeek) : CreatingDaysOfWeekCategory()
            data class OnTimeChanged(val time: String) : CreatingDaysOfWeekCategory()
            object Save : CreatingDaysOfWeekCategory()
        }

        sealed class CreatingEveryYearCategory : Categories() {
            data class OnDateChanged(val date: String) : CreatingEveryYearCategory()
            data class OnTimeChanged(val time: String) : CreatingEveryYearCategory()
            object Save : CreatingEveryYearCategory()
        }

        sealed class Notifications : Event() {
            data class Checked(val email: String) : Notifications()
            object Dismiss : Notifications()
            object Save : Notifications()
        }

        sealed class Subtasks : Event() {
            object Create : Subtasks()
            data class Delete(val title: String) : Subtasks()
            object Dismiss : Subtasks()
            object Save : Subtasks()
        }

        sealed class CreatingSubtask : Subtasks() {
            data class OnTitleChanged(val title: String) : CreatingSubtask()
            object Dismiss : CreatingSubtask()
            object Save : CreatingSubtask()
        }

        sealed class Products : Event() {
            object Create : Products()
            data class Delete(val title: String) : Products()
            object Dismiss : Products()
            object Save : Products()
        }

        sealed class CreatingProducts : Products() {
            data class OnTitleChanged(val title: String) : CreatingProducts()
            data class OnAmountChanged(val amount: String) : CreatingProducts()
            data class OnMeasureChanged(val measure: Product.Measure) : CreatingProducts()
            object Dismiss : CreatingProducts()
            object Save : CreatingProducts()
        }
    }

    data class State(
        val id: Long?,
        val status: TaskStatus?,

        val title: String,
        val description: String,
        val category: TaskCategory,
        val notifications: List<String>,
        val subtasks: List<Subtask>,
        val products: List<TaskProduct>
    ) {
        val creating: Boolean = id != null && status != null
        val titleValid: Boolean = title.isValidTaskTitle()
        val doneEnabled: Boolean = titleValid

        fun createFamilyTask() = FamilyTask(
            id = id ?: -1,
            category = category,
            status = status ?: TaskStatus.ACTIVE,
            title = title,
            desc = description,
            notificationEmails = notifications,
            products = products,
            subtasks = subtasks
        )

        companion object {
            fun createEmpty() = State(
                id = null,
                status = null,
                title = "",
                description = "",
                category = TaskCategory.OneShot,
                notifications = emptyList(),
                subtasks = emptyList(),
                products = emptyList()
            )

            fun createFromTask(task: FamilyTask) = State(
                id = task.id,
                status = task.status,
                title = task.title,
                description = task.desc,
                notifications = task.notificationEmails,
                products = task.products,
                subtasks = task.subtasks,
                category = task.category
            )
        }
    }

    data class CategoriesDialogState(
        val category: TaskCategory,
        val creatingState: CreatingState?
    ) {

        sealed class CreatingState {
            data class OneTimeCategory(
                val dateString: String,
                val timeString: String
            ) : CreatingState() {

                val dateValid = dateString.convertToRealFutureDate() != null

                val timeValid = timeString.convertToRealFutureTime() != null || timeString.isEmpty()

                val enabled = dateValid && timeValid

                fun toOneTimeCategory(): TaskCategory.OneTime {
                    val time = timeString.convertToRealFutureTime()
                    val date = dateString.convertToRealFutureDate()

                    val timeImportant = time != null
                    val localDateTime = if (timeImportant) {
                        date?.atTime(time)
                    } else {
                        date?.atStartOfDay()
                    } ?: LocalDateTime.now()

                    return TaskCategory.OneTime(
                        localDateTime = localDateTime,
                        isTimeImportant = timeImportant
                    )
                }

                companion object {
                    fun createEmpty() = OneTimeCategory(
                        dateString = "",
                        timeString = ""
                    )
                }
            }

            data class EveryYearCategory(
                val dateString: String,
                val timeString: String
            ) : CreatingState() {
                val dateValid = dateString.convertToRealFutureDate() != null
                val timeValid = timeString.convertToRealFutureTime() != null || timeString.isEmpty()

                val enabled = dateValid && timeValid

                fun toEveryYearCategory(): TaskCategory.Recurring.EveryYear {
                    val time = timeString.convertToRealFutureTime()
                    val date = dateString.convertToRealFutureDate()

                    val timeImportant = time != null
                    val localDateTime = if (timeImportant) {
                        date?.atTime(time)
                    } else {
                        date?.atStartOfDay()
                    } ?: LocalDateTime.now()

                    return TaskCategory.Recurring.EveryYear(
                        localDateTime = localDateTime,
                        isTimeImportant = timeImportant
                    )
                }

                companion object {
                    fun createEmpty() = EveryYearCategory(
                        dateString = "",
                        timeString = ""
                    )
                }
            }

            data class DaysOfWeekCategory(
                val daysOfWeek: List<DayOfWeek>,
                val timeString: String
            ) : CreatingState() {

                val timeValid = timeString.convertToRealFutureTime() != null || timeString.isEmpty()

                val enabled = daysOfWeek.isNotEmpty() && timeValid

                fun toDaysOfWeekCategory(): TaskCategory.Recurring.DaysOfWeek {
                    val time = timeString.convertToRealFutureTime()
                    val timeImportant = time != null

                    return TaskCategory.Recurring.DaysOfWeek(
                        days = daysOfWeek,
                        time = time ?: LocalTime.MAX,
                        isTimeImportant = timeImportant
                    )
                }

                companion object {
                    fun createEmpty() = DaysOfWeekCategory(
                        daysOfWeek = emptyList(),
                        timeString = ""
                    )
                }
            }
        }

        companion object {
            fun create(category: TaskCategory) = CategoriesDialogState(
                category = category,
                creatingState = null
            )

            fun createEmpty() = CategoriesDialogState(
                category = TaskCategory.OneShot,
                creatingState = null
            )
        }
    }

    data class NotificationDialogState(
        val items: List<NotificationItem>
    ) {
        data class NotificationItem(
            val email: String,
            val checked: Boolean,
            val name: String
        )

        fun toNotifications() = items.filter(NotificationItem::checked).map(NotificationItem::email)

        companion object {
            fun create(
                notifications: List<String>,
                allUsers: List<String>,
                names: List<LocalName>
            ): NotificationDialogState {
                val items = allUsers.map { email ->
                    NotificationItem(
                        email = email,
                        checked = notifications.contains(email),
                        name = names.find { it.email == email }?.localName ?: email
                    )
                }
                return NotificationDialogState(
                    items = items
                )
            }

            fun createEmpty() = NotificationDialogState(
                items = emptyList()
            )
        }
    }

    data class SubtasksDialogState(
        val items: List<Subtask>,
        val creatingState: CreatingState?
    ) {
        data class CreatingState(
            val title: String,
            val enabled: Boolean
        ) {

            fun toSubtask() = Subtask(
                id = -1,
                text = title,
                checked = false
            )

            companion object {
                fun createEmpty() = CreatingState(
                    title = "",
                    enabled = false
                )
            }
        }

        companion object {
            fun create(items: List<Subtask>) = SubtasksDialogState(
                items = items,
                creatingState = null
            )

            fun createEmpty() = SubtasksDialogState(
                items = emptyList(),
                creatingState = null
            )
        }
    }

    data class ProductDialogState(
        val items: List<TaskProduct>,
        val creatingState: CreatingState?
    ) {
        data class CreatingState(
            val title: String,
            val amount: Double?,
            val measure: Product.Measure?,
            val enabled: Boolean
        ) {
            fun copyWithUpdatedEnabled(state: ProductDialogState?) = copy(
                enabled = title.isValidProductName() && amount?.isValidQuantity() ?: true
                        && state?.items?.map(TaskProduct::title)?.all { it != title } == true
            )

            fun toProduct() = TaskProduct(
                id = -1,
                title = title,
                amount = amount,
                measure = measure.takeIf { amount != null },
                checked = false
            )

            companion object {
                fun createEmpty() = CreatingState(
                    title = "",
                    enabled = false,
                    amount = null,
                    measure = null
                )
            }
        }

        companion object {
            fun create(items: List<TaskProduct>) = ProductDialogState(
                items = items,
                creatingState = null
            )

            fun createEmpty() = ProductDialogState(
                items = emptyList(),
                creatingState = null
            )
        }
    }
}
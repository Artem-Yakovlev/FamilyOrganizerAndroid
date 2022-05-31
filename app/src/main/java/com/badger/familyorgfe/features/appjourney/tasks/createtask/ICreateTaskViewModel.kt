package com.badger.familyorgfe.features.appjourney.tasks.createtask

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.data.model.Subtask
import com.badger.familyorgfe.data.model.TaskProduct
import com.badger.familyorgfe.ext.isValidTaskTitle
import kotlinx.coroutines.flow.StateFlow

interface ICreateTaskViewModel : IBaseViewModel<ICreateTaskViewModel.Event> {

    val state: StateFlow<State>

    val notificationsDialogState: StateFlow<NotificationDialogState?>

    val subtasksDialogState: StateFlow<SubtasksDialogState?>

    val productsDialogState: StateFlow<ProductDialogState?>

    sealed class Event {

        sealed class Ordinal : Event() {
            object Init : Ordinal()
            object OnDoneClicked : Ordinal()

            data class OnTitleValueChanged(val value: String) : Ordinal()
            data class OnDescriptionValueChanged(val value: String) : Ordinal()

            data class OpenNotifications(val notifications: List<String>) : Ordinal()

            data class OpenSubtasks(val subtasks: List<Subtask>) : Ordinal()

            data class OpenProducts(val products: List<TaskProduct>) : Ordinal()
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
        val creating: Boolean,
        val doneEnabled: Boolean,
        val title: String,
        val description: String,
        val notifications: List<String>,
        val subtasks: List<Subtask>,
        val products: List<TaskProduct>
    ) {
        val titleValid: Boolean = title.isValidTaskTitle()
        val descriptionValid: Boolean = description.isNotEmpty()

        companion object {
            fun createEmpty() = State(
                creating = true,
                doneEnabled = false,
                title = "",
                description = "",
                notifications = emptyList(),
                subtasks = emptyList(),
                products = emptyList()
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

            fun toProduct() = TaskProduct(
                id = -1,
                title = title,
                amount = amount,
                measure = measure,
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
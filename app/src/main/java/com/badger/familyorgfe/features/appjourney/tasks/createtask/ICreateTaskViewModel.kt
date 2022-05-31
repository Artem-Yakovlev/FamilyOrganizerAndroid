package com.badger.familyorgfe.features.appjourney.tasks.createtask

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.ext.isValidTaskTitle
import kotlinx.coroutines.flow.StateFlow

interface ICreateTaskViewModel : IBaseViewModel<ICreateTaskViewModel.Event> {

    val state: StateFlow<State>

    val notificationsDialogState: StateFlow<NotificationDialogState?>

    sealed class Event {

        sealed class Ordinal : Event() {
            object Init : Ordinal()
            object OnDoneClicked : Ordinal()

            data class OnTitleValueChanged(val value: String) : Ordinal()
            data class OnDescriptionValueChanged(val value: String) : Ordinal()

            data class OpenNotifications(val notifications: List<String>) : Ordinal()
        }

        sealed class Notifications : Event() {
            data class Checked(val email: String) : Notifications()
            object Dismiss : Notifications()
            object Save : Notifications()
        }
    }

    data class State(
        val creating: Boolean,
        val doneEnabled: Boolean,
        val title: String,
        val description: String,
        val notifications: List<String>
    ) {
        val titleValid: Boolean = title.isValidTaskTitle()
        val descriptionValid: Boolean = description.isNotEmpty()

        companion object {
            fun createEmpty() = State(
                creating = true,
                doneEnabled = false,
                title = "",
                description = "",
                notifications = emptyList()
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
}
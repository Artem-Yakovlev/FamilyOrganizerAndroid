package com.badger.familyorgfe.features.appjourney.tasks.createtask

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.MAX_TASK_TITLE_LENGTH
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.appjourney.tasks.createtask.domain.CreateNotificationsDialogStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createNotificationsDialogStateUseCase: CreateNotificationsDialogStateUseCase
) : BaseViewModel(), ICreateTaskViewModel {

    override val state =
        MutableStateFlow(ICreateTaskViewModel.State.createEmpty())
    override val notificationsDialogState =
        MutableStateFlow<ICreateTaskViewModel.NotificationDialogState?>(null)

    override fun onEvent(event: ICreateTaskViewModel.Event) {
        when (event) {
            is ICreateTaskViewModel.Event.Ordinal -> onOrdinalEvent(event)
            is ICreateTaskViewModel.Event.Notifications -> onNotificationsEvent(event)
        }
    }

    private fun onOrdinalEvent(event: ICreateTaskViewModel.Event.Ordinal) {
        when (event) {
            is ICreateTaskViewModel.Event.Ordinal.Init -> {

            }
            is ICreateTaskViewModel.Event.Ordinal.OnDoneClicked -> {

            }
            is ICreateTaskViewModel.Event.Ordinal.OnDescriptionValueChanged -> {
                state.value = state.value.copy(description = event.value)
            }
            is ICreateTaskViewModel.Event.Ordinal.OnTitleValueChanged -> {
                state.value = state.value.copy(title = event.value.take(MAX_TASK_TITLE_LENGTH))
            }
            is ICreateTaskViewModel.Event.Ordinal.OpenNotifications -> longRunning {
                notificationsDialogState.value = createNotificationsDialogStateUseCase(
                    arg = event.notifications
                )
            }
        }
    }

    private fun onNotificationsEvent(event: ICreateTaskViewModel.Event.Notifications) {
        when (event) {
            is ICreateTaskViewModel.Event.Notifications.Checked -> {
                notificationsDialogState.value = notificationsDialogState.value?.copy(
                    items = notificationsDialogState.value?.items?.map { item ->
                        if (item.email == event.email) {
                            item.copy(checked = !item.checked)
                        } else {
                            item
                        }
                    }.orEmpty()
                )
            }
            is ICreateTaskViewModel.Event.Notifications.Dismiss -> {
                notificationsDialogState.value = null
            }
            is ICreateTaskViewModel.Event.Notifications.Save -> {
                state.value = state.value.copy(
                    notifications = notificationsDialogState.value?.toNotifications().orEmpty()
                )
                notificationsDialogState.value = null
            }
        }
    }
}
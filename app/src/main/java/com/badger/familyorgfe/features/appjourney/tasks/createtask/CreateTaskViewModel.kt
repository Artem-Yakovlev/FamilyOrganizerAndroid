package com.badger.familyorgfe.features.appjourney.tasks.createtask

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.MAX_TASK_TITLE_LENGTH
import com.badger.familyorgfe.ext.isValidTaskTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor() : BaseViewModel(), ICreateTaskViewModel {

    override val creating = MutableStateFlow(false)
    override val doneEnabled = MutableStateFlow(false)

    override val titleValue = MutableStateFlow("")
    override val titleError = MutableStateFlow<String?>(null)

    override val descriptionValue = MutableStateFlow("")
    override val descriptionError = MutableStateFlow<String?>(null)

    override fun onEvent(event: ICreateTaskViewModel.Event) {
        when (event) {
            is ICreateTaskViewModel.Event.Init -> {

            }
            is ICreateTaskViewModel.Event.OnDoneClicked -> {

            }
            is ICreateTaskViewModel.Event.OnDescriptionValueChanged -> {
                descriptionValue.value = event.value
                descriptionError.value = "".takeIf { event.value.isEmpty() }
            }
            is ICreateTaskViewModel.Event.OnTitleValueChanged -> {
                titleValue.value = event.value.take(MAX_TASK_TITLE_LENGTH)
                titleError.value = "".takeIf { !event.value.isValidTaskTitle() }
            }
        }
    }
}
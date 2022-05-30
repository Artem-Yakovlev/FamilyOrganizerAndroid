package com.badger.familyorgfe.features.appjourney.tasks.createtask

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface ICreateTaskViewModel : IBaseViewModel<ICreateTaskViewModel.Event> {

    val creating: StateFlow<Boolean>
    val doneEnabled: StateFlow<Boolean>

    val titleValue: StateFlow<String>
    val titleError: StateFlow<String?>

    val descriptionValue: StateFlow<String>
    val descriptionError: StateFlow<String?>

    sealed class Event {
        object Init : Event()
        object OnDoneClicked : Event()

        data class OnTitleValueChanged(val value: String) : Event()
        data class OnDescriptionValueChanged(val value: String) : Event()
    }
}
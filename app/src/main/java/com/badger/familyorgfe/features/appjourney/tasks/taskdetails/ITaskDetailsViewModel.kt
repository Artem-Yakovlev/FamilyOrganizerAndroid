package com.badger.familyorgfe.features.appjourney.tasks.taskdetails

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import kotlinx.coroutines.flow.StateFlow

interface ITaskDetailsViewModel : IBaseViewModel<ITaskDetailsViewModel.Event> {

    val familyTask: StateFlow<FamilyTask?>

    sealed class Event {
        object OpenEditing : Event()
    }
}
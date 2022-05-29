package com.badger.familyorgfe.features.appjourney.tasks.taskdetails

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.data.model.LocalName
import kotlinx.coroutines.flow.StateFlow

interface ITaskDetailsViewModel : IBaseViewModel<ITaskDetailsViewModel.Event> {

    val familyTask: StateFlow<FamilyTask?>

    val localNames: StateFlow<List<LocalName>>

    sealed class Event {
        object OpenEditing : Event()
    }
}
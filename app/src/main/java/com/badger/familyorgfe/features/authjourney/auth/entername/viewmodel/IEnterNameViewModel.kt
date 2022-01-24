package com.badger.familyorgfe.features.authjourney.auth.entername.viewmodel

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface IEnterNameViewModel : IBaseViewModel<IEnterNameViewModel.Event> {

    val name: StateFlow<String>

    val continueEnabled: StateFlow<Boolean>

    sealed class Event {
        data class NameUpdate(val query: String) : Event()
        object ContinueClick : Event()
        object SkipClick : Event()
    }
}
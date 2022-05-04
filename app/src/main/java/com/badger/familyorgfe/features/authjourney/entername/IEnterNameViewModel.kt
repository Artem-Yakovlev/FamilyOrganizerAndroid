package com.badger.familyorgfe.features.authjourney.entername

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface IEnterNameViewModel : IBaseViewModel<IEnterNameViewModel.Event> {

    val name: StateFlow<String>

    val continueEnabled: StateFlow<Boolean>

    val isLoading: StateFlow<Boolean>

    sealed class Event {
        object Init : Event()
        data class NameUpdate(val query: String) : Event()
        data class ContinueClick(val name: String) : Event()
        object SkipClick : Event()
    }
}
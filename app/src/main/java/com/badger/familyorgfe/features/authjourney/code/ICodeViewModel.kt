package com.badger.familyorgfe.features.authjourney.code

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface ICodeViewModel : IBaseViewModel<ICodeViewModel.Event> {

    val code: StateFlow<String>

    val continueEnabled: StateFlow<Boolean>

    val resendCodeEnabled: StateFlow<Boolean>

    sealed class Event {
        data class CodeUpdate(val query: String) : Event()
        object ContinueClicked : Event()
        object ResendCodeClicked : Event()
    }
}
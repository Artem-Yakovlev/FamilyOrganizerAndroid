package com.badger.familyorgfe.features.authjourney.code

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ICodeViewModel : IBaseViewModel<ICodeViewModel.Event> {

    val onCodeVerifiedAction: SharedFlow<Boolean>

    val email: StateFlow<String>

    val code: StateFlow<String>

    val continueEnabled: StateFlow<Boolean>

    val resendCodeEnabled: StateFlow<Boolean>

    val isLoading: StateFlow<Boolean>

    sealed class Event {
        data class OnArgument(val email: String) : Event()
        data class CodeUpdate(val query: String) : Event()
        data class ContinueClicked(val code: String) : Event()
        object ResendCodeClicked : Event()
    }
}
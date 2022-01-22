package com.badger.familyorgfe.features.authjourney.auth.code.viewmodel

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface ICodeViewModel : IBaseViewModel<ICodeViewModel.Event> {

    val mail: StateFlow<String>

    val continueEnabled: StateFlow<Boolean>

    sealed class Event {
        data class MailUpdate(val mail: String) : Event()
        object ContinueClick : Event()
    }
}
package com.badger.familyorgfe.features.authjourney.auth.mail.viewmodel

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface IMailViewModel : IBaseViewModel<IMailViewModel.Event> {

    val mail: StateFlow<String>

    val continueEnabled: StateFlow<Boolean>

    sealed class Event {
        data class MailUpdate(val query: String) : Event()
        object ContinueClick : Event()
    }
}
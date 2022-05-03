package com.badger.familyorgfe.features.authjourney.mail

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IMailViewModel : IBaseViewModel<IMailViewModel.Event> {

    val mail: StateFlow<String>

    val isLoading: StateFlow<Boolean>

    val continueEnabled: StateFlow<Boolean>

    val onEmailSentAction: SharedFlow<String>

    sealed class Event {
        data class MailUpdate(val query: String) : Event()
        data class ContinueClick(val email: String) : Event()
    }
}
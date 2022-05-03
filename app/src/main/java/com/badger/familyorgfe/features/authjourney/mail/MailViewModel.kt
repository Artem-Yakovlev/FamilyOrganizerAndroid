package com.badger.familyorgfe.features.authjourney.mail

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.isValidMail
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.authjourney.mail.IMailViewModel.Event
import com.badger.familyorgfe.features.authjourney.mail.domain.SendCodeLetterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MailViewModel @Inject constructor(
    private val sendCodeLetterUseCase: SendCodeLetterUseCase
) : BaseViewModel(), IMailViewModel {

    override val mail = MutableStateFlow("")

    override val isLoading = MutableStateFlow(false)

    override val continueEnabled = MutableStateFlow(false)

    override val onEmailSent = MutableStateFlow("")

    override fun onEvent(event: Event) {
        when (event) {
            is Event.MailUpdate -> handleMailUpdate(event.query)
            is Event.ContinueClick -> longRunning { handleContinueClick(event.email) }
        }
    }

    private fun handleMailUpdate(query: String) {
        mail.value = query
        continueEnabled.value = query.isValidMail()
    }

    private suspend fun handleContinueClick(email: String) {
        isLoading.value = true
        if (sendCodeLetterUseCase.invoke(email)) {
            onEmailSent.value = email
            delay(1)
            onEmailSent.value = ""
            isLoading.value = false
        } else {
            clearData()
        }
    }

    override fun clearData() {
        mail.value = ""
        isLoading.value = false
        continueEnabled.value = false
    }
}
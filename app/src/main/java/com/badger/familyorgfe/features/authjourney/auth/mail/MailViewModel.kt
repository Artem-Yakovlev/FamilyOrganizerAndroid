package com.badger.familyorgfe.features.authjourney.auth.mail

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.isValidMail
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.authjourney.auth.mail.IMailViewModel.Event
import com.badger.familyorgfe.features.authjourney.auth.mail.domain.SetMailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MailViewModel @Inject constructor(
    private val setMailUseCase: SetMailUseCase
) : BaseViewModel(), IMailViewModel {

    override val mail = MutableStateFlow("")

    override val continueEnabled = MutableStateFlow(false)

    override fun onEvent(event: Event) {
        when (event) {
            is Event.MailUpdate -> handleMailUpdate(event.query)
            is Event.ContinueClick -> longRunning { handleContinueClick() }
        }
    }

    private fun handleMailUpdate(query: String) {
        mail.value = query
        continueEnabled.value = query.isValidMail()
    }

    private suspend fun handleContinueClick() {
        setMailUseCase.invoke(Unit)
        // TODO: navigate to code screen
    }

    override fun clearData() = Unit
}
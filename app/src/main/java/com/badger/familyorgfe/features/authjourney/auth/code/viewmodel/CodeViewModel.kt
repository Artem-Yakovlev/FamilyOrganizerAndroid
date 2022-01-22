package com.badger.familyorgfe.features.authjourney.auth.code.viewmodel

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.features.authjourney.auth.code.viewmodel.ICodeViewModel.Event
import com.badger.familyorgfe.features.isValidMail
import com.badger.familyorgfe.features.longRunning
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
//    private val setMailUseCase: SetCodeUseCase
) : BaseViewModel(), ICodeViewModel {

    override val mail = MutableStateFlow("")

    override val continueEnabled = MutableStateFlow(false)

    override fun onEvent(event: Event) {
        when (event) {
            is Event.MailUpdate -> handleMailUpdate(event.mail)
            is Event.ContinueClick -> longRunning { handleContinueClick() }
        }
    }

    private fun handleMailUpdate(query: String) {
        mail.value = query
        continueEnabled.value = query.isValidMail()
    }

    private suspend fun handleContinueClick() {
        // TODO: navigate to code screen
    }

    override fun clearData() = Unit
}
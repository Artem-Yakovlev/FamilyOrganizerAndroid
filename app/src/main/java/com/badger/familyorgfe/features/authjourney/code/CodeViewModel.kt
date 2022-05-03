package com.badger.familyorgfe.features.authjourney.code

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.isValidMail
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.authjourney.code.ICodeViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
//    private val setMailUseCase: SetCodeUseCase
) : BaseViewModel(), ICodeViewModel {

    override val code = MutableStateFlow("")

    override val continueEnabled = MutableStateFlow(false)

    override val resendCodeEnabled = MutableStateFlow(false)

    override fun onEvent(event: Event) {
        when (event) {
            is Event.CodeUpdate -> handleCodeUpdate(event.query)
            is Event.ContinueClicked -> longRunning { handleContinueClick() }
            is Event.ResendCodeClicked -> longRunning { handleResendCodeClick() }
        }
    }

    private fun handleCodeUpdate(query: String) {
        code.value = query
        continueEnabled.value = query.isValidMail()
    }

    private suspend fun handleContinueClick() {
        // TODO: navigate to code screen
    }

    private suspend fun handleResendCodeClick() {
        // TODO: navigate to code screen
    }

    override fun clearData() = Unit
}
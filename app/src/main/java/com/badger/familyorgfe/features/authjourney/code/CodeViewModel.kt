package com.badger.familyorgfe.features.authjourney.code

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.source.auth.CheckCodeJson
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.authjourney.code.ICodeViewModel.Event
import com.badger.familyorgfe.features.authjourney.code.domain.VerifyCodeUseCase
import com.badger.familyorgfe.features.authjourney.mail.domain.SendCodeLetterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
    private val sendCodeLetterUseCase: SendCodeLetterUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase
) : BaseViewModel(), ICodeViewModel {

    private val _onCodeVerifiedAction = MutableSharedFlow<Boolean>()
    override val onCodeVerifiedAction = _onCodeVerifiedAction.asSharedFlow()

    override val email = MutableStateFlow("")

    override val code = MutableStateFlow("")

    override val continueEnabled = MutableStateFlow(false)

    override val resendCodeEnabled = MutableStateFlow(false)

    init {
        startResendDebounce()
    }

    override fun onEvent(event: Event) {
        when (event) {
            is Event.OnArgument -> onArgument(mail = event.email)
            is Event.CodeUpdate -> handleCodeUpdate(rawQuery = event.query)
            is Event.ContinueClicked -> longRunning { handleContinueClick(code = event.code) }
            is Event.ResendCodeClicked -> longRunning { handleResendCodeClick() }
        }
    }

    private fun onArgument(mail: String) {
        email.value = mail
    }

    private fun handleCodeUpdate(rawQuery: String) {
        val query = rawQuery.filter(Char::isDigit).take(CODE_LENGTH)
        code.value = query
        continueEnabled.value = query.length == CODE_LENGTH
    }

    private suspend fun handleContinueClick(code: String) {
        continueEnabled.value = false

        val form = CheckCodeJson.Form(
            email = email.value,
            code = code
        )
        _onCodeVerifiedAction.emit(verifyCodeUseCase(form))
        continueEnabled.value = true
    }

    private suspend fun handleResendCodeClick() {
        resendCodeEnabled.value = false
        if (sendCodeLetterUseCase(email.value)) {
            startResendDebounce()
        } else {
            resendCodeEnabled.value = true
        }
    }

    private fun startResendDebounce() {
        viewModelScope().launch {
            delay(RESEND_DEBOUNCE)
            resendCodeEnabled.value = true
        }
    }

    override fun clearData() = Unit

    companion object {
        private const val CODE_LENGTH = 4
        private const val RESEND_DEBOUNCE = 15000L
    }
}
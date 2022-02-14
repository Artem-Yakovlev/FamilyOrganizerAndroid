package com.badger.familyorgfe.features.authjourney.auth.code

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.isValidCode
import com.badger.familyorgfe.features.authjourney.auth.code.ICodeViewModel.Event
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.route
import com.badger.familyorgfe.features.authjourney.AuthJourneyType
import com.badger.familyorgfe.navigation.NavigationCommand.To
import com.badger.familyorgfe.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
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
        continueEnabled.value = query.isValidCode()
    }

    private suspend fun handleContinueClick() {
        navigationManager.navigate(To(AuthJourneyType.ENTER_NAME.route))
    }

    private suspend fun handleResendCodeClick() {
    }

    override fun clearData() = Unit
}
package com.badger.familyorgfe.features.authjourney.auth.welcome

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.authjourney.auth.welcome.IWelcomeViewModel.Event
import com.badger.familyorgfe.features.authjourney.auth.welcome.domain.EndAuthUseCase
import com.badger.familyorgfe.navigation.NavigationCommand.To
import com.badger.familyorgfe.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val endAuthUseCase: EndAuthUseCase
) : BaseViewModel(), IWelcomeViewModel {

    override fun onEvent(event: Event) {
        when (event) {
            is Event.ContinueClicked -> longRunning { handleContinueClicked() }
        }
    }

    private suspend fun handleContinueClicked() {
        endAuthUseCase.invoke(Unit)
//        navigationManager.navigate(To())
    }

}
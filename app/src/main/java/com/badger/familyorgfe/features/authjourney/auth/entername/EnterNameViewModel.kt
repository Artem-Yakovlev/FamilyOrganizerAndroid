package com.badger.familyorgfe.features.authjourney.auth.entername

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.features.authjourney.auth.entername.IEnterNameViewModel.Event
import com.badger.familyorgfe.features.authjourney.auth.entername.domain.SetNameUseCase
import com.badger.familyorgfe.ext.isValidName
import com.badger.familyorgfe.ext.longRunning
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EnterNameViewModel @Inject constructor(
    private val setNameUseCase: SetNameUseCase
) : BaseViewModel(), IEnterNameViewModel {

    companion object {
        private const val EMPTY_NAME = ""
    }

    override val name = MutableStateFlow("")

    override val continueEnabled = MutableStateFlow(false)

    override fun onEvent(event: Event) {
        when (event) {
            is Event.NameUpdate -> handleNameUpdate(event.query)
            is Event.ContinueClick -> longRunning { handleContinueClick() }
            is Event.SkipClick -> longRunning { handleSkipClick() }
        }
    }

    private fun handleNameUpdate(query: String) {
        name.value = query
        continueEnabled.value = query.isValidName()
    }

    private suspend fun handleContinueClick() {
        setNameUseCase.invoke(name.value)
        // TODO: navigate to welcome screen
    }

    private suspend fun handleSkipClick() {
        setNameUseCase.invoke(EMPTY_NAME)
        // TODO: navigate to welcome screen
    }

    override fun clearData() = Unit
}
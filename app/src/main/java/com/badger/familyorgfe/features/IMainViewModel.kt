package com.badger.familyorgfe.features

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface IMainViewModel : IBaseViewModel<Nothing> {

    val isAuthorized: StateFlow<AuthState>

    sealed class AuthState {
        object Auth : AuthState()
        object NoAuth : AuthState()
        object Loading : AuthState()
    }
}
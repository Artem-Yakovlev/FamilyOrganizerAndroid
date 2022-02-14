package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.commoninteractors.GetMainUserUseCase
import com.badger.familyorgfe.commoninteractors.SetIsUserAuthorizedUseCase
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getMainUserUseCase: GetMainUserUseCase,
    private val setIsUserAuthorizedUseCase: SetIsUserAuthorizedUseCase
) : BaseViewModel(), IProfileViewModel {

    override val mainUser: StateFlow<User> = getMainUserUseCase(Unit)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope(Dispatchers.Default),
            started = SharingStarted.Lazily,
            initialValue = User.createEmpty()
        )

    override val showLogoutDialog = MutableStateFlow(false)

    override fun onEvent(event: IProfileViewModel.Event) {
        when (event) {
            IProfileViewModel.Event.OnLogoutClick -> {
                showLogoutDialog.value = true
            }
            IProfileViewModel.Event.OnLogoutAccepted -> longRunning { handleLogout() }
            IProfileViewModel.Event.OnLogoutDismiss -> {
                showLogoutDialog.value = false
            }
        }
    }

    private suspend fun handleLogout() {
        showLogoutDialog.value = false
        setIsUserAuthorizedUseCase.invoke(false)
    }
}
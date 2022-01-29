package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.User
import kotlinx.coroutines.flow.StateFlow

interface IProfileViewModel : IBaseViewModel<IProfileViewModel.Event> {

    val showLogoutDialog: StateFlow<Boolean>

    val mainUser: StateFlow<User>

    sealed class Event {
        object OnLogoutClick : Event()
        object OnLogoutDismiss : Event()
        object OnLogoutAccepted : Event()
    }
}
package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import kotlinx.coroutines.flow.StateFlow

interface IProfileViewModel : IBaseViewModel<IProfileViewModel.Event> {

    val showLogoutDialog: StateFlow<Boolean>

    val mainUser: StateFlow<FamilyMember>

    val members: StateFlow<List<FamilyMember>>

    sealed class Event {
        object OnLogoutClick : Event()
        object OnLogoutDismiss : Event()
        object OnLogoutAccepted : Event()
    }
}
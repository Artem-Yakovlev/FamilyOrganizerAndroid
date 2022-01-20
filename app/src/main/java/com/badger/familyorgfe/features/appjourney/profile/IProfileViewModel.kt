package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.IBaseViewModel

interface IProfileViewModel : IBaseViewModel<IProfileViewModel.Event> {

    sealed class Event {
        object OnLogoutClick : Event()
    }
}
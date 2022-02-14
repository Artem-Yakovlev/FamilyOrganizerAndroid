package com.badger.familyorgfe.features.authjourney.auth.welcome

import com.badger.familyorgfe.base.IBaseViewModel

interface IWelcomeViewModel : IBaseViewModel<IWelcomeViewModel.Event> {

    sealed class Event {
        object ContinueClicked : Event()
    }

}
package com.badger.familyorgfe.features.appjourney

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface IAppJourneyViewModel : IBaseViewModel<IAppJourneyViewModel.Event> {

    val selectedBottomNavItem: StateFlow<BottomNavigationType>

    sealed class Event {
        data class OnBottomNavItemSelected(val item: BottomNavigationType) : Event()
    }
}
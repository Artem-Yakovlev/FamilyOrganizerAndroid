package com.badger.familyorgfe.features.appjourney

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.features.appjourney.bottomnavigation.BottomNavigationType
import kotlinx.coroutines.flow.MutableStateFlow

class AppJourneyViewModel : BaseViewModel(), IAppJourneyViewModel {

    override val selectedBottomNavItem = MutableStateFlow(BottomNavigationType.FRIDGE_ROUTE)

    override fun onEvent(event: IAppJourneyViewModel.Event) {
        when (event) {
            is IAppJourneyViewModel.Event.OnBottomNavItemSelected -> {
                selectedBottomNavItem.value = event.item
            }
        }
    }

    override fun clearData() = Unit
}


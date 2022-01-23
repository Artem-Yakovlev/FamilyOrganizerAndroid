package com.badger.familyorgfe.features.appjourney.fridge

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.StateFlow

interface IFridgeViewModel : IBaseViewModel<IFridgeViewModel.Event> {

    val items: StateFlow<List<FridgeItem>>

    val expandedItemId: StateFlow<String?>

    sealed class Event {
        data class OnItemExpanded(val id: String) : Event()
        object OnItemCollapsed : Event()
    }
}
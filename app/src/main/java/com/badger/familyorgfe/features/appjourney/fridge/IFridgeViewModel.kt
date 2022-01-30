package com.badger.familyorgfe.features.appjourney.fridge

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.StateFlow

interface IFridgeViewModel : IBaseViewModel<IFridgeViewModel.Event> {

    /**
     * Search
     * */

    val isSearchActive: StateFlow<Boolean>

    val searchQuery: StateFlow<String>

    /**
     * Items
     * */

    val items: StateFlow<List<FridgeItem>>

    val expandedItemId: StateFlow<String?>

    /**
     * Dialogs
     * */

    val deleteItemDialog: StateFlow<FridgeItem?>

    sealed class Event {
        data class OnSearchQueryChanged(val query: String) : Event()
        object OpenSearch : Event()
        object CloseSearch : Event()
        object ClearSearchQuery : Event()

        data class OnItemExpanded(val id: String) : Event()
        object OnItemCollapsed : Event()

        data class RequestDeleteItemDialog(val item: FridgeItem) : Event()
        data class DeleteItem(val item: FridgeItem) : Event()
        object DismissDialogs : Event()
    }
}
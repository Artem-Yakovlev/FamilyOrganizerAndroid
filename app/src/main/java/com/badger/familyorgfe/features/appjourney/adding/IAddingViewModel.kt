package com.badger.familyorgfe.features.appjourney.adding

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.StateFlow

interface IAddingViewModel : IBaseViewModel<IAddingViewModel.Event> {

    /**
     * Toolbar
     * */

    val doneEnabled: StateFlow<Boolean>

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
        object OnBackClicked : Event()
        object OnAddClicked : Event()
        object OnDoneClicked : Event()

        data class OnItemExpanded(val id: String) : Event()
        object OnItemCollapsed : Event()

        data class RequestDeleteItemDialog(val item: FridgeItem) : Event()
        data class DeleteItem(val item: FridgeItem) : Event()
        object DismissDeleteDialog : Event()
    }
}
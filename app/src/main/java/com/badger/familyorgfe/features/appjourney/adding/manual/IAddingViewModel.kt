package com.badger.familyorgfe.features.appjourney.adding.manual

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.features.appjourney.common.productbottomsheet.ProductBottomSheetState
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.StateFlow

interface IAddingViewModel : IBaseViewModel<IAddingViewModel.Event> {

    /**
     * Toolbar
     * */

    val doneEnabled: StateFlow<Boolean>

    val successAdded: StateFlow<Boolean>

    /**
     * Items
     * */

    val items: StateFlow<List<FridgeItem>>

    val expandedItemId: StateFlow<Long?>

    /**
     * Dialogs
     * */

    val deleteItemDialog: StateFlow<FridgeItem?>

    val manualAddingState: StateFlow<ProductBottomSheetState?>

    sealed class Event {
        object OnBackClicked : Event()
        object OnAddClicked : Event()
        object OnDoneClicked : Event()

        data class OnItemExpanded(val id: Long) : Event()
        object OnItemCollapsed : Event()

        data class RequestDeleteItemDialog(val item: FridgeItem) : Event()
        data class DeleteItem(val item: FridgeItem) : Event()
        object DismissDeleteDialog : Event()

        object OnBottomSheetClose : Event()
        data class OnManualAddingTitleChanged(val title: String) : Event()
        data class OnManualAddingQuantityChanged(val quantity: String) : Event()
        data class OnManualAddingMeasureChanged(val measure: Product.Measure) : Event()
        data class OnManualAddingExpirationDateChanged(val date: String) : Event()
        data class OnManualAddingExpirationDaysChanged(val days: String) : Event()
        object OnCreateClicked : Event()
    }
}
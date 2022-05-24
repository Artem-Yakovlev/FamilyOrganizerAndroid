package com.badger.familyorgfe.features.appjourney.fridge

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.features.appjourney.common.productbottomsheet.ProductBottomSheetState
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

    val expandedItemId: StateFlow<Long?>

    /**
     * Dialogs
     * */

    val deleteItemDialog: StateFlow<FridgeItem?>

    val editingState: StateFlow<ProductBottomSheetState?>

    sealed class Event {
        sealed class Ordinal : Event() {
            object Init : Ordinal()

            data class OnSearchQueryChanged(val query: String) : Ordinal()
            object OpenSearch : Ordinal()
            object CloseSearch : Ordinal()
            object ClearSearchQuery : Ordinal()

            data class OnItemExpanded(val id: Long) : Ordinal()
            object OnItemCollapsed : Ordinal()

            data class RequestDeleteItemDialog(val item: FridgeItem) : Ordinal()
            data class DeleteItem(val item: FridgeItem) : Ordinal()
            object DismissDialogs : Ordinal()

            data class OnEditClicked(val item: FridgeItem) : Ordinal()
        }

        sealed class Editing : Event() {
            object OnBottomSheetClose : Editing()
            data class OnTitleChanged(val title: String) : Editing()
            data class OnQuantityChanged(val quantity: String) : Editing()
            data class OnMeasureChanged(val measure: Product.Measure) : Editing()
            data class OnExpirationDateChanged(val date: String) : Editing()
            data class OnExpirationDaysChanged(val days: String) : Editing()
            object OnActionClicked : Editing()
        }
    }
}
package com.badger.familyorgfe.features.appjourney.adding

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.isValidProductName
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.LocalDate
import kotlin.random.Random

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

    val expandedItemId: StateFlow<String?>

    /**
     * Dialogs
     * */

    val deleteItemDialog: StateFlow<FridgeItem?>

    val manualAddingState: StateFlow<ManualAddingState?>

    data class ManualAddingState(
        val title: String,
        val quantity: Double?,
        val measure: Product.Measure,
        val expirationDateString: String?,
        val expirationDaysString: String?,
        val expirationDate: LocalDate?
    ) {
        val createEnabled = title.isValidProductName() && (expirationDate != null
                || expirationDateString.isNullOrEmpty() && expirationDaysString.isNullOrEmpty())

        val isDateError = (expirationDateString != null || expirationDaysString != null)
                && expirationDate == null

        fun createProduct() = if (createEnabled) {
            Product(
                id = Random.nextLong().toString(),
                name = title,
                quantity = quantity,
                measure = measure,
                category = Product.Category.DEFAULT,
                expiryDate = expirationDate?.atStartOfDay()
            )
        } else {
            null
        }

        companion object {
            fun createEmpty() = ManualAddingState(
                title = "",
                quantity = null,
                measure = Product.Measure.KILOGRAM,
                expirationDate = null,
                expirationDateString = null,
                expirationDaysString = null
            )
        }
    }

    sealed class Event {
        object OnBackClicked : Event()
        object OnAddClicked : Event()
        object OnDoneClicked : Event()

        data class OnItemExpanded(val id: String) : Event()
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
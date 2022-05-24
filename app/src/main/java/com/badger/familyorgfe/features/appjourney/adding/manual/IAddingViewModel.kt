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
     * Screen
     * */

    val items: StateFlow<List<FridgeItem>>

    val expandedItemId: StateFlow<Long?>

    val isAutoAdding: StateFlow<Boolean>

    /**
     * Dialogs
     * */

    val deleteItemDialog: StateFlow<FridgeItem?>

    val manualAddingState: StateFlow<ProductBottomSheetState?>

    val editingState: StateFlow<ProductBottomSheetState?>

    sealed class Event {

        sealed class Ordinal : Event() {
            object OnBackClicked : Ordinal()
            object OnAddClicked : Ordinal()
            data class OnEditClicked(val item: FridgeItem) : Ordinal()
            object OnDoneClicked : Ordinal()

            data class OnItemExpanded(val id: Long) : Ordinal()
            object OnItemCollapsed : Ordinal()
            object OnAddLongClicked : Ordinal()

            data class RequestDeleteItemDialog(val item: FridgeItem) : Ordinal()
            data class DeleteItem(val item: FridgeItem) : Ordinal()
            object DismissDeleteDialog : Ordinal()

            data class OnProductsScanned(val products: List<Product>) : Ordinal()
        }

        sealed class ProductEvent : Event() {
            abstract val creating: Boolean

            fun asCreating() = copyWithTarget(creating = true)

            fun asEditing() = copyWithTarget(creating = false)

            protected abstract fun copyWithTarget(creating: Boolean): ProductEvent

            data class OnBottomSheetClose(
                override val creating: Boolean = true
            ) : ProductEvent() {
                override fun copyWithTarget(creating: Boolean): ProductEvent {
                    return copy(creating = creating)
                }
            }

            data class OnTitleChanged(
                override val creating: Boolean = true,
                val title: String,
            ) : ProductEvent() {
                override fun copyWithTarget(creating: Boolean): ProductEvent {
                    return copy(creating = creating)
                }
            }

            data class OnQuantityChanged(
                override val creating: Boolean = true,
                val quantity: String
            ) : ProductEvent() {
                override fun copyWithTarget(creating: Boolean): ProductEvent {
                    return copy(creating = creating)
                }
            }

            data class OnMeasureChanged(
                override val creating: Boolean = true,
                val measure: Product.Measure
            ) : ProductEvent() {
                override fun copyWithTarget(creating: Boolean): ProductEvent {
                    return copy(creating = creating)
                }
            }

            data class OnExpirationDateChanged(
                override val creating: Boolean = true,
                val date: String
            ) : ProductEvent() {
                override fun copyWithTarget(creating: Boolean): ProductEvent {
                    return copy(creating = creating)
                }
            }

            data class OnExpirationDaysChanged(
                override val creating: Boolean = true,
                val days: String
            ) : ProductEvent() {
                override fun copyWithTarget(creating: Boolean): ProductEvent {
                    return copy(creating = creating)
                }
            }

            data class OnActionClicked(
                override val creating: Boolean = true
            ) : ProductEvent() {
                override fun copyWithTarget(creating: Boolean): ProductEvent {
                    return copy(creating = creating)
                }
            }
        }
    }
}
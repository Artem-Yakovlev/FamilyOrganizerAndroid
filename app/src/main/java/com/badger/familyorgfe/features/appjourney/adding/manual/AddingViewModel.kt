package com.badger.familyorgfe.features.appjourney.adding.manual

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.*
import com.badger.familyorgfe.features.appjourney.adding.manual.domain.AddProductUseCase
import com.badger.familyorgfe.features.appjourney.common.productbottomsheet.ProductBottomSheetState
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddingViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase
) : BaseViewModel(), IAddingViewModel {

    private val products = MutableStateFlow<List<Product>>(emptyList())
    private val isLoading = MutableStateFlow(false)
    override val successAdded = MutableStateFlow(false)

    override val items: StateFlow<List<FridgeItem>> = products
        .map { products -> products.map(Product::toFridgeItem) }
        .stateIn(viewModelScope(), started = SharingStarted.Lazily, initialValue = emptyList())

    override val expandedItemId = MutableStateFlow<Long?>(null)
    override val deleteItemDialog = MutableStateFlow<FridgeItem?>(null)

    override val manualAddingState = MutableStateFlow<ProductBottomSheetState?>(null)
    override val doneEnabled = items.map(List<FridgeItem>::isNotEmpty)
        .stateIn(viewModelScope(), started = SharingStarted.Lazily, initialValue = false)

    override val editingState = MutableStateFlow<ProductBottomSheetState?>(null)

    override fun onEvent(event: IAddingViewModel.Event) {
        when (event) {
            is IAddingViewModel.Event.Ordinal -> {
                onOrdinalEvent(event)
            }
            is IAddingViewModel.Event.ProductEvent -> {
                onBottomSheetEvent(
                    state = if (event.creating) {
                        manualAddingState
                    } else {
                        editingState
                    },
                    onAction = { product ->
                        if (event.creating) {
                            products.value = (products.value + product).sortedBy(Product::name)
                        } else {
                            products.value = products.value.map { item ->
                                item.takeIf { it.id == product.id }?.let { product } ?: item
                            }.sortedBy(Product::name)
                        }
                    },
                    event = event
                )
            }
        }
    }

    private fun onOrdinalEvent(event: IAddingViewModel.Event.Ordinal) {
        when (event) {
            is IAddingViewModel.Event.Ordinal.OnAddClicked -> {
                manualAddingState.value = ProductBottomSheetState.createEmpty()
            }
            is IAddingViewModel.Event.Ordinal.OnEditClicked -> {
                editingState.value = ProductBottomSheetState.createFromFridgeItem(event.item)
            }
            is IAddingViewModel.Event.Ordinal.OnDoneClicked -> longRunning {
                if (!isLoading.value) {
                    isLoading.value = true
                    val success = addProductUseCase(products.value)
                    isLoading.value = success
                    successAdded.value = success
                }
            }
            is IAddingViewModel.Event.Ordinal.OnItemCollapsed -> {
                expandedItemId.value = null
            }
            is IAddingViewModel.Event.Ordinal.OnItemExpanded -> {
                expandedItemId.value = event.id
            }
            is IAddingViewModel.Event.Ordinal.DeleteItem -> {
                products.value = products.value.filter { it.id != event.item.id }
                deleteItemDialog.value = null
            }
            is IAddingViewModel.Event.Ordinal.DismissDeleteDialog -> {
                deleteItemDialog.value = null
            }
            is IAddingViewModel.Event.Ordinal.RequestDeleteItemDialog -> {
                deleteItemDialog.value = event.item
            }
            is IAddingViewModel.Event.Ordinal.OnBackClicked -> {
                clearData()
            }
        }
    }

    private fun onBottomSheetEvent(
        state: MutableStateFlow<ProductBottomSheetState?>,
        onAction: (Product) -> Unit,
        event: IAddingViewModel.Event.ProductEvent
    ) {
        when (event) {
            is IAddingViewModel.Event.ProductEvent.OnBottomSheetClose -> {
                state.value = null
            }
            is IAddingViewModel.Event.ProductEvent.OnTitleChanged -> {
                state.value = state.value?.copy(
                    title = event.title
                )
            }
            is IAddingViewModel.Event.ProductEvent.OnQuantityChanged -> {
                event.quantity.toDoubleOrNull()?.let { quantity ->
                    state.value = state.value?.copy(
                        quantity = quantity
                    )
                }
            }
            is IAddingViewModel.Event.ProductEvent.OnMeasureChanged -> {
                state.value = state.value?.copy(
                    measure = event.measure
                )
            }
            is IAddingViewModel.Event.ProductEvent.OnExpirationDateChanged -> {
                val expirationDate = event.date.convertToRealFutureDate()
                val expirationDays = expirationDate.toExpirationDays()

                state.value = state.value?.copy(
                    expirationDateString = event.date,
                    expirationDate = expirationDate,
                    expirationDaysString = expirationDays
                )
            }
            is IAddingViewModel.Event.ProductEvent.OnExpirationDaysChanged -> {
                val expirationDays = event.days.toIntOrNull()?.takeIf { it >= 0 }
                val expirationDate = expirationDays?.toExpirationDate()
                val expirationDateString = expirationDate?.toExpirationDateString().orEmpty()

                state.value = state.value?.copy(
                    expirationDateString = expirationDateString,
                    expirationDate = expirationDate,
                    expirationDaysString = expirationDays?.toString().orEmpty()
                )
            }
            is IAddingViewModel.Event.ProductEvent.onActionClicked -> {
                val product = state.value?.createProduct() ?: return
                state.value = null
                onAction(product)
            }
        }
    }

    override fun clearData() {
        products.value = emptyList()
        expandedItemId.value = null
        deleteItemDialog.value = null
        manualAddingState.value = null
        isLoading.value = false
    }
}
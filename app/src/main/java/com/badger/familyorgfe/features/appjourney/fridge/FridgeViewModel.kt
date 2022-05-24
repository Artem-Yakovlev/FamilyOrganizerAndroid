package com.badger.familyorgfe.features.appjourney.fridge

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.*
import com.badger.familyorgfe.features.appjourney.common.productbottomsheet.ProductBottomSheetState
import com.badger.familyorgfe.features.appjourney.fridge.domain.DeleteFridgeItemUseCase
import com.badger.familyorgfe.features.appjourney.fridge.domain.GetAllFridgeItemsUseCase
import com.badger.familyorgfe.features.appjourney.fridge.domain.SearchInFridgeItemsUseCase
import com.badger.familyorgfe.features.appjourney.fridge.domain.UpdateProductUseCase
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(
    getAllFridgeItemsUseCase: GetAllFridgeItemsUseCase,
    private val searchInFridgeItemsUseCase: SearchInFridgeItemsUseCase,
    private val deleteFridgeItemUseCase: DeleteFridgeItemUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : BaseViewModel(), IFridgeViewModel {

    private val refreshProductsCrutch: MutableStateFlow<Long> = MutableStateFlow(0L)

    override val isSearchActive = MutableStateFlow(false)

    override val searchQuery = MutableStateFlow("")

    private val rawItems = refreshProductsCrutch
        .flatMapLatest { flow { emit(getAllFridgeItemsUseCase(Unit)) } }
        .map { items -> items.sortedBy(FridgeItem::name) }

    override val items = combine(
        flow = rawItems,
        flow2 = searchQuery,
        transform = { items, query -> searchInFridgeItemsUseCase(items to query) }
    ).stateIn(
        scope = viewModelScope(Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )


    override val expandedItemId = MutableStateFlow<Long?>(null)

    override val deleteItemDialog = MutableStateFlow<FridgeItem?>(null)

    override val editingState = MutableStateFlow<ProductBottomSheetState?>(null)


    override fun onEvent(event: IFridgeViewModel.Event) {
        when (event) {
            is IFridgeViewModel.Event.Ordinal -> onOrdinalEvent(event)
            is IFridgeViewModel.Event.Editing -> onEditingEvent(event)
        }
    }

    private fun onOrdinalEvent(event: IFridgeViewModel.Event.Ordinal) {
        when (event) {
            is IFridgeViewModel.Event.Ordinal.Init -> {
                refreshProductsCrutch.value = System.currentTimeMillis()
            }
            is IFridgeViewModel.Event.Ordinal.OnItemCollapsed -> {
                expandedItemId.value = null
            }
            is IFridgeViewModel.Event.Ordinal.OnItemExpanded -> {
                expandedItemId.value = event.id
            }
            is IFridgeViewModel.Event.Ordinal.ClearSearchQuery -> {
                searchQuery.value = ""
            }
            is IFridgeViewModel.Event.Ordinal.CloseSearch -> {
                isSearchActive.value = false
                searchQuery.value = ""
            }
            is IFridgeViewModel.Event.Ordinal.OnSearchQueryChanged -> {
                searchQuery.value = event.query.replaceFirstChar(Char::uppercaseChar)
            }
            is IFridgeViewModel.Event.Ordinal.OpenSearch -> {
                isSearchActive.value = true
            }
            is IFridgeViewModel.Event.Ordinal.DismissDialogs -> {
                deleteItemDialog.value = null
            }
            is IFridgeViewModel.Event.Ordinal.RequestDeleteItemDialog -> {
                expandedItemId.value = null
                deleteItemDialog.value = event.item
            }
            is IFridgeViewModel.Event.Ordinal.DeleteItem -> {
                viewModelScope(
                    dispatcher = Dispatchers.IO,
                    onError = {
                        deleteItemDialog.value = null
                    }
                ).launch {
                    deleteFridgeItemUseCase(event.item)
                    deleteItemDialog.value = null
                    refreshProductsCrutch.value = System.currentTimeMillis()
                }
            }
            is IFridgeViewModel.Event.Ordinal.OnEditClicked -> {
                editingState.value = ProductBottomSheetState.createFromFridgeItem(event.item)
                expandedItemId.value = null
            }
        }
    }

    private fun onEditingEvent(event: IFridgeViewModel.Event.Editing) {
        when (event) {
            is IFridgeViewModel.Event.Editing.OnBottomSheetClose -> {
                editingState.value = null
            }
            is IFridgeViewModel.Event.Editing.OnTitleChanged -> {
                editingState.value = editingState.value?.copy(
                    title = event.title
                )
            }
            is IFridgeViewModel.Event.Editing.OnQuantityChanged -> {
                event.quantity.toDoubleOrNull()?.let { quantity ->
                    editingState.value = editingState.value?.copy(
                        quantity = quantity
                    )
                }
            }
            is IFridgeViewModel.Event.Editing.OnMeasureChanged -> {
                editingState.value = editingState.value?.copy(
                    measure = event.measure
                )
            }
            is IFridgeViewModel.Event.Editing.OnExpirationDateChanged -> {
                val expirationDate = event.date.convertToRealFutureDate()
                val expirationDays = expirationDate.toExpirationDays()

                editingState.value = editingState.value?.copy(
                    expirationDateString = event.date,
                    expirationDate = expirationDate,
                    expirationDaysString = expirationDays
                )
            }
            is IFridgeViewModel.Event.Editing.OnExpirationDaysChanged -> {
                val expirationDays = event.days.toIntOrNull()?.takeIf { it >= 0 }
                val expirationDate = expirationDays?.toExpirationDate()
                val expirationDateString = expirationDate?.toExpirationDateString().orEmpty()

                editingState.value = editingState.value?.copy(
                    expirationDateString = expirationDateString,
                    expirationDate = expirationDate,
                    expirationDaysString = expirationDays?.toString().orEmpty()
                )
            }
            is IFridgeViewModel.Event.Editing.OnActionClicked -> longRunning {
                val product = editingState.value?.createProduct() ?: return@longRunning Unit
                editingState.value = null
                val success = updateProductUseCase(product)
                if (success) {
                    refreshProductsCrutch.value = System.currentTimeMillis()
                }
            }
        }
    }
}
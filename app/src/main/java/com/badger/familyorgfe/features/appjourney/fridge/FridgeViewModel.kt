package com.badger.familyorgfe.features.appjourney.fridge

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.fridge.domain.DeleteFridgeItemUseCase
import com.badger.familyorgfe.features.appjourney.fridge.domain.GetAllFridgeItemsUseCase
import com.badger.familyorgfe.features.appjourney.fridge.domain.SearchInFridgeItemsUseCase
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
    private val deleteFridgeItemUseCase: DeleteFridgeItemUseCase
) : BaseViewModel(), IFridgeViewModel {

    private val refreshProductsCrutch: MutableStateFlow<Long> = MutableStateFlow(0L)

    override val isSearchActive = MutableStateFlow(false)

    override val searchQuery = MutableStateFlow("")

    private val rawItems = refreshProductsCrutch.flatMapLatest { getAllFridgeItemsUseCase(Unit) }

    override val items = combine(
        flow = rawItems,
        flow2 = searchQuery,
        transform = { items, query -> searchInFridgeItemsUseCase(items to query) }
    ).stateIn(
        scope = viewModelScope(Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    override val expandedItemId = MutableStateFlow<String?>(null)

    override val deleteItemDialog = MutableStateFlow<FridgeItem?>(null)

    override fun onEvent(event: IFridgeViewModel.Event) {
        when (event) {
            is IFridgeViewModel.Event.Init -> {
                refreshProductsCrutch.value = System.currentTimeMillis()
            }
            is IFridgeViewModel.Event.OnItemCollapsed -> {
                expandedItemId.value = null
            }
            is IFridgeViewModel.Event.OnItemExpanded -> {
                expandedItemId.value = event.id
            }
            is IFridgeViewModel.Event.ClearSearchQuery -> {
                searchQuery.value = ""
            }
            is IFridgeViewModel.Event.CloseSearch -> {
                isSearchActive.value = false
                searchQuery.value = ""
            }
            is IFridgeViewModel.Event.OnSearchQueryChanged -> {
                searchQuery.value = event.query.replaceFirstChar(Char::uppercaseChar)
            }
            is IFridgeViewModel.Event.OpenSearch -> {
                isSearchActive.value = true
            }
            is IFridgeViewModel.Event.DismissDialogs -> {
                deleteItemDialog.value = null
            }
            is IFridgeViewModel.Event.RequestDeleteItemDialog -> {
                expandedItemId.value = null
                deleteItemDialog.value = event.item
            }
            is IFridgeViewModel.Event.DeleteItem -> {
                viewModelScope(
                    dispatcher = Dispatchers.IO,
                    onError = {
                        deleteItemDialog.value = null
                    }
                ).launch {
                    deleteFridgeItemUseCase(event.item)
                    deleteItemDialog.value = null
                }
            }
        }
    }
}
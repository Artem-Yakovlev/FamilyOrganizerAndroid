package com.badger.familyorgfe.features.appjourney.fridge

import androidx.lifecycle.viewModelScope
import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.features.appjourney.fridge.domain.GetAllFridgeItemsUseCase
import com.badger.familyorgfe.features.appjourney.fridge.domain.SearchInFridgeItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(
    getAllFridgeItemsUseCase: GetAllFridgeItemsUseCase,
    private val searchInFridgeItemsUseCase: SearchInFridgeItemsUseCase
) : BaseViewModel(), IFridgeViewModel {

    override val isSearchActive = MutableStateFlow(false)

    override val searchQuery = MutableStateFlow("")

    private val rawItems = getAllFridgeItemsUseCase(Unit)

    override val items = combine(
        flow = rawItems,
        flow2 = searchQuery,
        transform = { items, query -> searchInFridgeItemsUseCase(items to query) }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    override val expandedItemId = MutableStateFlow<String?>(null)

    override fun onEvent(event: IFridgeViewModel.Event) {
        when (event) {
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
        }
    }
}
package com.badger.familyorgfe.features.appjourney.adding

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddingViewModel @Inject constructor() : BaseViewModel(), IAddingViewModel {

    override val items = MutableStateFlow<List<FridgeItem>>(emptyList())
    override val expandedItemId = MutableStateFlow<String?>(null)
    override val deleteItemDialog = MutableStateFlow<FridgeItem?>(null)

    override fun onEvent(event: IAddingViewModel.Event) {
        when (event) {
            is IAddingViewModel.Event.OnAddClicked -> {
                items.value = items.value + FridgeItem.mock(Random.nextLong().toString())
            }
            is IAddingViewModel.Event.OnDoneClicked -> {

            }
            is IAddingViewModel.Event.OnItemCollapsed -> {
                expandedItemId.value = null
            }
            is IAddingViewModel.Event.OnItemExpanded -> {
                expandedItemId.value = event.id
            }
            is IAddingViewModel.Event.DeleteItem -> {
                items.value = items.value.filter { it.id != event.item.id }
                deleteItemDialog.value = null
            }
            is IAddingViewModel.Event.DismissDeleteDialog -> {
                deleteItemDialog.value = null
            }
            is IAddingViewModel.Event.RequestDeleteItemDialog -> {
                deleteItemDialog.value = event.item
            }
            IAddingViewModel.Event.OnBackClicked -> {
                clearData()
            }
        }
    }

    override fun clearData() {
        items.value = emptyList()
        expandedItemId.value = null
        deleteItemDialog.value = null
    }
}
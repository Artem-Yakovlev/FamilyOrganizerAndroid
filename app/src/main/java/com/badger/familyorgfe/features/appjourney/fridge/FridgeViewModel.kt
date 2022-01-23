package com.badger.familyorgfe.features.appjourney.fridge

import androidx.lifecycle.viewModelScope
import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.features.appjourney.fridge.domain.GetAllFridgeItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(
    getAllFridgeItems: GetAllFridgeItems
) : BaseViewModel(), IFridgeViewModel {

    override val items = getAllFridgeItems(Unit).stateIn(
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
        }
    }
}
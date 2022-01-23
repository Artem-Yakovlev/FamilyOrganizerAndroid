package com.badger.familyorgfe.features.appjourney.fridge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeListItem

@Composable
fun FridgeScreen(
    modifier: Modifier,
    viewModel: IFridgeViewModel = hiltViewModel<FridgeViewModel>()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        val currentSearchQuery by viewModel.searchQuery.collectAsState()
        val isSearchActive by viewModel.isSearchActive.collectAsState()

        FridgeToolbar(
            isSearchActive = isSearchActive,
            currentSearchQuery = currentSearchQuery,
            startSearch = {
                viewModel.onEvent(event = IFridgeViewModel.Event.OpenSearch)
            },
            closeSearch = {
                viewModel.onEvent(event = IFridgeViewModel.Event.CloseSearch)
            },
            searchQueryChanged = { query ->
                viewModel.onEvent(event = IFridgeViewModel.Event.OnSearchQueryChanged(query))
            },
            clearSearchQuery = {
                viewModel.onEvent(event = IFridgeViewModel.Event.ClearSearchQuery)
            }
        )

        val fridgeItems by viewModel.items.collectAsState()
        val expandedItemId by viewModel.expandedItemId.collectAsState()

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            fridgeItems.forEach { item ->
                item {
                    FridgeListItem(
                        item = item,
                        isExpanded = item.id == expandedItemId,
                        onExpand = {
                            val event = IFridgeViewModel.Event.OnItemExpanded(item.id)
                            viewModel.onEvent(event)
                        },
                        onCollapse = {
                            val event = IFridgeViewModel.Event.OnItemCollapsed
                            viewModel.onEvent(event)
                        },
                        onEdit = {},
                        onDelete = {}
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                }
            }
        }
    }
}

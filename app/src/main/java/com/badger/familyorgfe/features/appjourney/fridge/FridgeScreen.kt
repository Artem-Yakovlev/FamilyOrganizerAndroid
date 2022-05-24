package com.badger.familyorgfe.features.appjourney.fridge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.features.appjourney.common.ProductBottomSheet
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeListItem
import com.badger.familyorgfe.ui.elements.BaseDialog
import com.badger.familyorgfe.utils.BackHandler

@Composable
fun FridgeScreen(
    modifier: Modifier,
    viewModel: IFridgeViewModel = hiltViewModel<FridgeViewModel>()
) {
    val editingState by viewModel.editingState.collectAsState()

    val onBack: () -> Unit = {
        when {
            editingState != null -> {
                viewModel.onEvent(
                    IFridgeViewModel.Event.Editing.OnBottomSheetClose
                )
            }
            else -> {

            }
        }
    }
    BackHandler(onBack = onBack, enabled = editingState != null)

    LaunchedEffect(Unit) {
        viewModel.onEvent(
            IFridgeViewModel.Event.Ordinal.Init
        )
    }

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
                viewModel.onEvent(event = IFridgeViewModel.Event.Ordinal.OpenSearch)
            },
            closeSearch = {
                viewModel.onEvent(event = IFridgeViewModel.Event.Ordinal.CloseSearch)
            },
            searchQueryChanged = { query ->
                viewModel.onEvent(event = IFridgeViewModel.Event.Ordinal.OnSearchQueryChanged(query))
            },
            clearSearchQuery = {
                viewModel.onEvent(event = IFridgeViewModel.Event.Ordinal.ClearSearchQuery)
            }
        )

        val fridgeItems by viewModel.items.collectAsState()
        val expandedItemId by viewModel.expandedItemId.collectAsState()
        val deleteDialog by viewModel.deleteItemDialog.collectAsState()

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(
                items = fridgeItems,
                key = { item -> item.id }
            ) { item ->
                FridgeListItem(
                    item = item,
                    isExpanded = item.id == expandedItemId,
                    onExpand = {
                        viewModel.onEvent(
                            IFridgeViewModel.Event.Ordinal.OnItemExpanded(item.id)
                        )
                    },
                    onCollapse = {
                        viewModel.onEvent(
                            IFridgeViewModel.Event.Ordinal.OnItemCollapsed
                        )
                    },
                    onEdit = { editableItem ->
                        viewModel.onEvent(
                            IFridgeViewModel.Event.Ordinal.OnEditClicked(editableItem)
                        )
                    },
                    onDelete = { deletedItem ->
                        viewModel.onEvent(
                            IFridgeViewModel.Event.Ordinal.RequestDeleteItemDialog(deletedItem)
                        )
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        }

        deleteDialog?.let { fridgeItem ->

            BaseDialog(
                titleText = stringResource(id = R.string.fridge_toolbar_title),
                descriptionText = stringResource(
                    id = R.string.fridge_delete_product_description,
                    fridgeItem.name
                ),
                dismissText = stringResource(id = R.string.fridge_delete_product_dismiss),
                actionText = stringResource(id = R.string.fridge_delete_product_action),
                onDismissClicked = {
                    viewModel.onEvent(
                        IFridgeViewModel.Event.Ordinal.DismissDialogs
                    )
                },
                onActionClicked = {
                    viewModel.onEvent(
                        IFridgeViewModel.Event.Ordinal.DeleteItem(fridgeItem)
                    )
                })

        }
    }

    ProductBottomSheet(
        modifier = modifier,
        creating = false,
        onBack = onBack,
        productBottomSheetState = editingState,
        onTitleChanged = {
            viewModel.onEvent(
                IFridgeViewModel.Event.Editing.OnTitleChanged(title = it)
            )
        },
        onQuantityChanged = {
            viewModel.onEvent(
                IFridgeViewModel.Event.Editing.OnQuantityChanged(quantity = it)
            )
        },
        onMeasureChanged = {
            viewModel.onEvent(
                IFridgeViewModel.Event.Editing.OnMeasureChanged(measure = it)
            )
        },
        onExpirationDaysChanged = {
            viewModel.onEvent(
                IFridgeViewModel.Event.Editing.OnExpirationDaysChanged(days = it)
            )
        },
        onExpirationDateChanged = {
            viewModel.onEvent(
                IFridgeViewModel.Event.Editing.OnExpirationDateChanged(date = it)
            )
        },
        onCreateClicked = {
            viewModel.onEvent(
                IFridgeViewModel.Event.Editing.OnActionClicked
            )
        }
    )
}

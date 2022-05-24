package com.badger.familyorgfe.features.appjourney.adding.manual

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.features.appjourney.common.ProductBottomSheet
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeListItem
import com.badger.familyorgfe.ui.elements.BaseDialog
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.utils.BackHandler
import kotlin.math.roundToInt


@Composable
fun AddingScreen(
    modifier: Modifier,
    navOnBack: () -> Unit,
    viewModel: IAddingViewModel
) {
    val manualAddingState by viewModel.manualAddingState.collectAsState()
    val onBack: () -> Unit = {
        when {
            manualAddingState != null -> {
                val event = IAddingViewModel.Event.OnBottomSheetClose
                viewModel.onEvent(event)
            }
            else -> {
                val event = IAddingViewModel.Event.OnBackClicked
                viewModel.onEvent(event)
                navOnBack()
            }
        }
    }

    val successAdded by viewModel.successAdded.collectAsState()
    LaunchedEffect(key1 = successAdded) {
        if (successAdded) {
            onBack()
        }
    }

    BackHandler(onBack = onBack)

    Screen(
        modifier = modifier,
        viewModel = viewModel,
        onBack = onBack
    )

    ProductBottomSheet(
        modifier = modifier,
        creating = true,
        onBack = onBack,
        productBottomSheetState = manualAddingState,
        onTitleChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.OnManualAddingTitleChanged(it)
            )
        },
        onQuantityChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.OnManualAddingQuantityChanged(it)
            )
        },
        onMeasureChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.OnManualAddingMeasureChanged(it)
            )
        },
        onExpirationDaysChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event
                    .OnManualAddingExpirationDaysChanged(it)
            )
        },
        onExpirationDateChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event
                    .OnManualAddingExpirationDateChanged(it)
            )
        },
        onCreateClicked = {
            viewModel.onEvent(
                IAddingViewModel.Event.OnCreateClicked
            )
        }
    )
}

/**
 * Screen
 * */

@Composable
private fun Screen(
    modifier: Modifier,
    viewModel: IAddingViewModel,
    onBack: () -> Unit
) {
    val doneEnabled by viewModel.doneEnabled.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        val fabHeightPx = with(LocalDensity.current) { 72.dp.roundToPx().toFloat() }
        val fabOffsetHeightPx = remember { mutableStateOf(0f) }
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                    val delta = available.y
                    val newOffset = fabOffsetHeightPx.value + delta
                    fabOffsetHeightPx.value = newOffset.coerceIn(-fabHeightPx, 0f)

                    return Offset.Zero
                }
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Toolbar(
                onBackClicked = onBack,
                doneEnabled = doneEnabled,
                onDoneClicked = {
                    val event = IAddingViewModel.Event.OnDoneClicked
                    viewModel.onEvent(event)
                }
            )
            Listing(
                modifier = modifier,
                viewModel = viewModel,
                nestedScrollConnection = nestedScrollConnection
            )
        }
        Fab(
            fabOffsetHeightPx = fabOffsetHeightPx.value,
            viewModel = viewModel
        )
    }
}

@Composable
private fun Toolbar(
    onBackClicked: () -> Unit,
    doneEnabled: Boolean,
    onDoneClicked: () -> Unit
) {
    BaseToolbar {

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onBackClicked() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )

        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.adding_toolbar_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            color = FamilyOrganizerTheme.colors.blackPrimary
        )

        AnimatedVisibility(visible = doneEnabled) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onDoneClicked() },
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = null,
                tint = FamilyOrganizerTheme.colors.blackPrimary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun ColumnScope.Listing(
    modifier: Modifier,
    viewModel: IAddingViewModel,
    nestedScrollConnection: NestedScrollConnection
) {
    val fridgeItems by viewModel.items.collectAsState()
    val expandedItemId by viewModel.expandedItemId.collectAsState()
    val deleteDialog by viewModel.deleteItemDialog.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .weight(1f)
            .nestedScroll(nestedScrollConnection),
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
                    val event = IAddingViewModel.Event.OnItemExpanded(item.id)
                    viewModel.onEvent(event)
                },
                onCollapse = {
                    val event = IAddingViewModel.Event.OnItemCollapsed
                    viewModel.onEvent(event)
                },
                onEdit = {

                },
                onDelete = { deletedItem ->
                    val event = IAddingViewModel.Event.RequestDeleteItemDialog(deletedItem)
                    viewModel.onEvent(event)
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
                val event = IAddingViewModel.Event.DismissDeleteDialog
                viewModel.onEvent(event)
            },
            onActionClicked = {
                val event = IAddingViewModel.Event.DeleteItem(fridgeItem)
                viewModel.onEvent(event)
            })

    }
}

@Composable
private fun BoxScope.Fab(
    fabOffsetHeightPx: Float,
    viewModel: IAddingViewModel
) {
    FloatingActionButton(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
            .offset { IntOffset(x = 0, y = -fabOffsetHeightPx.roundToInt()) },
        backgroundColor = FamilyOrganizerTheme.colors.primary,
        onClick = {
            val event = IAddingViewModel.Event.OnAddClicked
            viewModel.onEvent(event)
        }
    ) {
        Icon(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.whitePrimary
        )
    }
}


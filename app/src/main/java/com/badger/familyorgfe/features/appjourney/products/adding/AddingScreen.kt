package com.badger.familyorgfe.features.appjourney.products.adding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ext.clickableWithoutIndication
import com.badger.familyorgfe.features.appjourney.bottomnavigation.ProductsNavigationType
import com.badger.familyorgfe.features.appjourney.common.ProductBottomSheet
import com.badger.familyorgfe.features.appjourney.products.adding.model.UpdatableTask
import com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem.FridgeListItem
import com.badger.familyorgfe.ui.elements.BaseActionButton
import com.badger.familyorgfe.ui.elements.BaseDialog
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.style.checkBoxColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.utils.BackHandler
import com.badger.familyorgfe.utils.fabNestedScroll
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt


@Composable
fun AddingScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: IAddingViewModel = hiltViewModel<AddingViewModel>()
) {
    val manualAddingState by viewModel.manualAddingState.collectAsState()
    val editingState by viewModel.editingState.collectAsState()
    val updatableTask by viewModel.updatableTasks.collectAsState()

    val onBack: () -> Unit = {
        when {
            manualAddingState != null -> {
                val event = IAddingViewModel.Event.ProductEvent.OnBottomSheetClose().asCreating()
                viewModel.onEvent(event)
            }
            editingState != null -> {
                val event = IAddingViewModel.Event.ProductEvent.OnBottomSheetClose().asEditing()
                viewModel.onEvent(event)
            }
            updatableTask != null -> {
                val event = IAddingViewModel.Event.TasksEvent.Dismiss
                viewModel.onEvent(event)
            }
            else -> {
                val event = IAddingViewModel.Event.Ordinal.OnBackClicked
                viewModel.onEvent(event)
                navController.popBackStack()
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
        openQrScanner = {
            navController.navigate(ProductsNavigationType.AUTO_ADDING_SCREEN.route)
        },
        onBack = onBack
    )

    ProductBottomSheet(
        modifier = modifier,
        creating = true,
        onBack = onBack,
        productBottomSheetState = manualAddingState,
        onTitleChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnTitleChanged(title = it).asCreating()
            )
        },
        onQuantityChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnQuantityChanged(quantity = it).asCreating()
            )
        },
        onMeasureChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnMeasureChanged(measure = it).asCreating()
            )
        },
        onExpirationDaysChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnExpirationDaysChanged(days = it).asCreating()
            )
        },
        onExpirationDateChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnExpirationDateChanged(date = it).asCreating()
            )
        },
        onCreateClicked = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnActionClicked().asCreating()
            )
        }
    )

    ProductBottomSheet(
        modifier = modifier,
        creating = false,
        onBack = onBack,
        productBottomSheetState = editingState,
        onTitleChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnTitleChanged(title = it).asEditing()
            )
        },
        onQuantityChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnQuantityChanged(quantity = it).asEditing()
            )
        },
        onMeasureChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnMeasureChanged(measure = it).asEditing()
            )
        },
        onExpirationDaysChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnExpirationDaysChanged(days = it).asEditing()
            )
        },
        onExpirationDateChanged = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnExpirationDateChanged(date = it).asEditing()
            )
        },
        onCreateClicked = {
            viewModel.onEvent(
                IAddingViewModel.Event.ProductEvent.OnActionClicked().asEditing()
            )
        }
    )

    UpdateTasksBottomSheet(
        modifier = modifier,
        tasks = updatableTask,
        onChecked = { taskId, checked ->
            val event = IAddingViewModel.Event.TasksEvent.OnChecked(taskId, checked)
            viewModel.onEvent(event)
        },
        onContinueClicked = {
            val event = IAddingViewModel.Event.TasksEvent.OnContinue
            viewModel.onEvent(event)
        },
        onBack = {
            val event = IAddingViewModel.Event.TasksEvent.Dismiss
            viewModel.onEvent(event)
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
    openQrScanner: () -> Unit,
    onBack: () -> Unit
) {
    val doneEnabled by viewModel.doneEnabled.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        val fabHeightPx = with(LocalDensity.current) { 72.dp.roundToPx().toFloat() }
        val fabOffsetHeightPx = remember { mutableStateOf(0f) }
        val nestedScrollConnection = remember {
            fabNestedScroll(
                fabHeightPx = fabHeightPx,
                fabOffsetHeightPx = fabOffsetHeightPx
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Toolbar(
                onBackClicked = onBack,
                doneEnabled = doneEnabled,
                onDoneClicked = {
                    val event = IAddingViewModel.Event.Ordinal.OnDoneClicked
                    viewModel.onEvent(event)
                }
            )
            Listing(
                modifier = modifier,
                viewModel = viewModel,
                nestedScrollConnection = nestedScrollConnection
            )
        }

        val autoMode by viewModel.isAutoAdding.collectAsState()
        Fab(
            fabOffsetHeightPx = fabOffsetHeightPx.value,
            autoMode = autoMode,
            onClick = { longClicked ->
                val event = if (longClicked) {
                    IAddingViewModel.Event.Ordinal.OnAddLongClicked
                } else {
                    if (autoMode) {
                        openQrScanner()
                        IAddingViewModel.Event.Ordinal.OnAddLongClicked
                    } else {
                        IAddingViewModel.Event.Ordinal.OnAddClicked
                    }
                }
                viewModel.onEvent(event)
            }
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
                    val event = IAddingViewModel.Event.Ordinal.OnItemExpanded(item.id)
                    viewModel.onEvent(event)
                },
                onCollapse = {
                    val event = IAddingViewModel.Event.Ordinal.OnItemCollapsed
                    viewModel.onEvent(event)
                },
                onEdit = {
                    val event = IAddingViewModel.Event.Ordinal.OnEditClicked(it)
                    viewModel.onEvent(event)
                },
                onDelete = { deletedItem ->
                    val event = IAddingViewModel.Event.Ordinal.RequestDeleteItemDialog(deletedItem)
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
                val event = IAddingViewModel.Event.Ordinal.DismissDeleteDialog
                viewModel.onEvent(event)
            },
            onActionClicked = {
                val event = IAddingViewModel.Event.Ordinal.DeleteItem(fridgeItem)
                viewModel.onEvent(event)
            })

    }
}

private const val LONG_CLICK_DELAY = 150L

@Composable
private fun BoxScope.Fab(
    fabOffsetHeightPx: Float,
    autoMode: Boolean,
    onClick: (Boolean) -> Unit
) {
    val interactionSource = MutableInteractionSource()
    var lastInteraction by remember { mutableStateOf<Pair<Interaction?, Long>>(null to 0L) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            val currentMillis = System.currentTimeMillis()

            if (interaction is PressInteraction.Release &&
                lastInteraction.first is PressInteraction.Press
            ) {
                val longClick = currentMillis - lastInteraction.second >= LONG_CLICK_DELAY
                onClick(longClick)
            }
            lastInteraction = interaction to currentMillis
        }
    }

    FloatingActionButton(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
            .offset { IntOffset(x = 0, y = -fabOffsetHeightPx.roundToInt()) },
        backgroundColor = FamilyOrganizerTheme.colors.primary,
        interactionSource = interactionSource,
        onClick = {},
    ) {
        Icon(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.Center),
            painter = painterResource(
                id = if (autoMode) {
                    R.drawable.ic_qr_code_scanner
                } else {
                    R.drawable.ic_add
                }
            ),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.whitePrimary
        )
    }
}


/**
 * Tasks
 * */

@Composable
private fun UpdateTasksBottomSheet(
    modifier: Modifier,
    tasks: List<UpdatableTask>?,
    onChecked: (Long, Boolean) -> Unit,
    onContinueClicked: () -> Unit,
    onBack: () -> Unit
) {
    AnimatedVisibility(
        visible = tasks != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .clickableWithoutIndication(onBack),
            color = FamilyOrganizerTheme.colors.blackPrimary.copy(alpha = 0.35f),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 0.dp),
                    contentAlignment = Alignment.Center,
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                                .background(FamilyOrganizerTheme.colors.whitePrimary)
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 32.dp)
                                .clickableWithoutIndication { }
                        ) {
                            tasks?.let { updatableTasks ->
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                ) {
                                    item {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp),
                                            text = stringResource(id = R.string.adding_update_tasks_product_title),
                                            style = FamilyOrganizerTheme.textStyle.body.copy(
                                                fontSize = 18.sp, fontWeight = FontWeight.Medium
                                            ),
                                            color = FamilyOrganizerTheme.colors.darkGray
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    items(updatableTasks) { task ->
                                        UpdatableTaskItem(task = task, onChecked = onChecked)
                                    }
                                    item {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        BaseActionButton(
                                            onAction = onContinueClicked,
                                            text = stringResource(id = R.string.adding_update_tasks_product_button),
                                            enabled = true
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                }
                            }
                        }
                    }
                )
            }
        )
    }
}

@Composable
private fun UpdatableTaskItem(
    task: UpdatableTask,
    onChecked: (Long, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Checkbox(
            checked = task.checked,
            onCheckedChange = { onChecked(task.taskId, !task.checked) },
            colors = checkBoxColors()
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = task.title,
            maxLines = 1,
            style = FamilyOrganizerTheme.textStyle.body,
            color = if (task.checked) {
                FamilyOrganizerTheme.colors.darkClay
            } else {
                FamilyOrganizerTheme.colors.darkGray
            }
        )
    }
}

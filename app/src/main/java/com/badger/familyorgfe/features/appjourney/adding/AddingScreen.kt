package com.badger.familyorgfe.features.appjourney.adding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.clickableWithoutIndication
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeListItem
import com.badger.familyorgfe.ui.elements.BaseDialog
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.utils.BackHandler
import kotlin.math.roundToInt

@Composable
fun AddingScreen(
    modifier: Modifier,
    navOnBack: () -> Unit,
    viewModel: IAddingViewModel = hiltViewModel<AddingViewModel>()
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
    BackHandler(onBack = onBack)

    Screen(
        modifier = modifier,
        viewModel = viewModel,
        onBack = onBack
    )

    AnimatedVisibility(
        visible = manualAddingState != null,
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
                                .clickableWithoutIndication {  }
                        ) {
                            manualAddingState?.let { state ->
                                BottomSheetContent(viewModel = viewModel, manualAddingState = state)
                            }
                        }
                    }
                )

            }
        )
    }
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
                onDoneClicked = {}
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

/**
 * ManualAddingBottomSheet
 * */

@Composable
private fun ColumnScope.BottomSheetContent(
    manualAddingState: IAddingViewModel.ManualAddingState,
    viewModel: IAddingViewModel
) {

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(id = R.string.bottom_sheet_adding_title),
        style = FamilyOrganizerTheme.textStyle.headline3.copy(fontSize = 20.sp),
        color = FamilyOrganizerTheme.colors.blackPrimary
    )
    Spacer(modifier = Modifier.height(22.dp))

    /**
     * Info
     * */

    Text(
        modifier = Modifier.align(Alignment.Start),
        text = stringResource(id = R.string.bottom_sheet_adding_info),
        style = FamilyOrganizerTheme.textStyle.body.copy(fontWeight = FontWeight.Medium),
        color = FamilyOrganizerTheme.colors.blackPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))

    AddingBottomSheetTextInput(
        modifier = Modifier.fillMaxWidth(),
        value = manualAddingState.title,
        hint = stringResource(id = R.string.bottom_sheet_adding_hint_title),
        onValueChange = {
            val event = IAddingViewModel.Event.OnManualAddingTitleChanged(it)
            viewModel.onEvent(event)
        }
    )
    Spacer(modifier = Modifier.height(16.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
        AddingBottomSheetTextInput(
            modifier = Modifier.weight(1f),
            value = manualAddingState.quantity?.toString().orEmpty(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            hint = stringResource(id = R.string.bottom_sheet_adding_hint_quantity),
            onValueChange = {
                val event = IAddingViewModel.Event.OnManualAddingQuantityChanged(it)
                viewModel.onEvent(event)
            }
        )
        Spacer(modifier = Modifier.width(8.dp))

        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            modifier = Modifier.weight(1f),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = getMeasureString(manualAddingState.measure),
                onValueChange = {},
                textStyle = FamilyOrganizerTheme.textStyle.input,
                colors = outlinedTextFieldColors(),
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(
                            id = if (expanded) {
                                R.drawable.ic_profile_status_arrow_up
                            } else {
                                R.drawable.ic_profile_status_arrow_down
                            }
                        ),
                        contentDescription = null,
                        tint = FamilyOrganizerTheme.colors.darkClay
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                Product.Measure.values()
                    .forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onEvent(
                                    IAddingViewModel.Event.OnManualAddingMeasureChanged(
                                        selectionOption
                                    )
                                )
                                expanded = false
                            }
                        ) {
                            Text(
                                text = getMeasureString(measure = selectionOption),
                                style = FamilyOrganizerTheme.textStyle.body.copy(fontWeight = FontWeight.Medium),
                                color = FamilyOrganizerTheme.colors.blackPrimary
                            )
                        }
                    }
            }
        }

    }

    /**
     * Expiration
     * */

    Spacer(modifier = Modifier.height(22.dp))

    Text(
        modifier = Modifier.align(Alignment.Start),
        text = stringResource(id = R.string.bottom_sheet_adding_expiration),
        style = FamilyOrganizerTheme.textStyle.body.copy(fontWeight = FontWeight.Medium),
        color = FamilyOrganizerTheme.colors.blackPrimary
    )

    Spacer(modifier = Modifier.height(8.dp))

    AddingBottomSheetTextInput(
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value = "",
        hint = stringResource(id = R.string.bottom_sheet_adding_expiration_days),
        onValueChange = {
            val event = IAddingViewModel.Event.OnManualAddingExpirationDaysChanged(it)
            viewModel.onEvent(event)
        }
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(id = R.string.bottom_sheet_adding_expiration_and),
        style = FamilyOrganizerTheme.textStyle.body.copy(fontWeight = FontWeight.Medium),
        color = FamilyOrganizerTheme.colors.blackPrimary
    )
    Spacer(modifier = Modifier.height(4.dp))
    AddingBottomSheetTextInput(
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        value = "",
        hint = stringResource(id = R.string.bottom_sheet_adding_expiration_date),
        onValueChange = {
            val event = IAddingViewModel.Event.OnManualAddingExpirationDateChanged(it)
            viewModel.onEvent(event)
        }
    )
    Spacer(modifier = Modifier.height(40.dp))

    Button(
        onClick = {
            val event = IAddingViewModel.Event.OnCreateClicked
            viewModel.onEvent(event)
        },
        enabled = manualAddingState.createEnabled,
        colors = buttonColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = stringResource(R.string.create_text).uppercase(),
            color = FamilyOrganizerTheme.colors.whitePrimary,
            style = FamilyOrganizerTheme.textStyle.button,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun AddingBottomSheetTextInput(
    modifier: Modifier,
    value: String,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        textStyle = FamilyOrganizerTheme.textStyle.input,
        keyboardOptions = keyboardOptions,
        colors = outlinedTextFieldColors(),
        placeholder = { Text(text = hint) }
    )
}

@Composable
private fun getMeasureString(measure: Product.Measure) = when (measure) {
    Product.Measure.LITER -> stringResource(id = R.string.adding_measure_litter)
    Product.Measure.KILOGRAM -> stringResource(id = R.string.adding_measure_kg)
    Product.Measure.THINGS -> stringResource(id = R.string.adding_measure_things)
}
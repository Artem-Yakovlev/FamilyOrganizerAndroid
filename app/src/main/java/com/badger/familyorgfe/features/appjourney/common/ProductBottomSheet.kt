package com.badger.familyorgfe.features.appjourney.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.clickableWithoutIndication
import com.badger.familyorgfe.features.appjourney.common.productbottomsheet.ProductBottomSheetState
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

/**
 * ManualAddingBottomSheet
 * */

@Composable
fun ProductBottomSheet(
    modifier: Modifier,
    creating: Boolean,
    onBack: () -> Unit,
    productBottomSheetState: ProductBottomSheetState?,
    onTitleChanged: (String) -> Unit,
    onQuantityChanged: (String) -> Unit,
    onMeasureChanged: (Product.Measure) -> Unit,
    onExpirationDaysChanged: (String) -> Unit,
    onExpirationDateChanged: (String) -> Unit,
    onCreateClicked: () -> Unit
) {
    AnimatedVisibility(
        visible = productBottomSheetState != null,
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
                            productBottomSheetState?.let { state ->
                                ProductBottomSheetContent(
                                    productBottomSheetState = state,
                                    creating = creating,
                                    onTitleChanged = onTitleChanged,
                                    onQuantityChanged = onQuantityChanged,
                                    onMeasureChanged = onMeasureChanged,
                                    onExpirationDaysChanged = onExpirationDaysChanged,
                                    onExpirationDateChanged = onExpirationDateChanged,
                                    onCreateClicked = onCreateClicked
                                )
                            }
                        }
                    }
                )

            }
        )
    }
}

@Composable
private fun ColumnScope.ProductBottomSheetContent(
    creating: Boolean,
    productBottomSheetState: ProductBottomSheetState,
    onTitleChanged: (String) -> Unit,
    onQuantityChanged: (String) -> Unit,
    onMeasureChanged: (Product.Measure) -> Unit,
    onExpirationDaysChanged: (String) -> Unit,
    onExpirationDateChanged: (String) -> Unit,
    onCreateClicked: () -> Unit
) {

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(
            id = if (creating) {
                R.string.bottom_sheet_adding_title
            } else {
                R.string.bottom_sheet_editing_title
            }
        ),
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
        value = productBottomSheetState.title,
        hint = stringResource(id = R.string.bottom_sheet_adding_hint_title),
        onValueChange = onTitleChanged
    )
    Spacer(modifier = Modifier.height(16.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
        AddingBottomSheetTextInput(
            modifier = Modifier.weight(1f),
            value = productBottomSheetState.quantity?.toString().orEmpty(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            hint = stringResource(id = R.string.bottom_sheet_adding_hint_quantity),
            onValueChange = onQuantityChanged
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
                value = getMeasureString(productBottomSheetState.measure),
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
                                onMeasureChanged(selectionOption)
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
        value = productBottomSheetState.expirationDaysString.orEmpty(),
        hint = stringResource(id = R.string.bottom_sheet_adding_expiration_days),
        onValueChange = onExpirationDaysChanged,
        isError = productBottomSheetState.isDateError
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
        value = productBottomSheetState.expirationDateString.orEmpty(),
        hint = stringResource(id = R.string.bottom_sheet_adding_expiration_date),
        onValueChange = onExpirationDateChanged,
        isError = productBottomSheetState.isDateError
    )
    Spacer(modifier = Modifier.height(40.dp))

    Button(
        onClick = onCreateClicked,
        enabled = productBottomSheetState.createEnabled,
        colors = buttonColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = stringResource(
                if (creating) {
                    R.string.bottom_sheet_adding_button
                } else {
                    R.string.bottom_sheet_editing_button
                }
            ).uppercase(),
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
    onValueChange: (String) -> Unit,
    isError: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        isError = isError,
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
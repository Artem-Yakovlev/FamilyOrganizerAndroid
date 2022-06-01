package com.badger.familyorgfe.features.appjourney.tasks.createtask

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
import com.badger.familyorgfe.data.model.Subtask
import com.badger.familyorgfe.data.model.TaskProduct
import com.badger.familyorgfe.ext.clickableWithoutIndication
import com.badger.familyorgfe.features.appjourney.common.AddingBottomSheetTextInput
import com.badger.familyorgfe.features.appjourney.common.getStringForMeasure
import com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem.getMeasureString
import com.badger.familyorgfe.ui.elements.BaseActionButton
import com.badger.familyorgfe.ui.elements.BaseOutlinedButton
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.checkBoxColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

/**
 * Category
 * */

@Composable
fun CategoryEditingDialog(
    state: ICreateTaskViewModel.CategoriesDialogState?,
    onOneShotCategorySelected: () -> Unit,
    onOneTimeCategorySelected: () -> Unit,
    onDaysOfWeekCategorySelected: () -> Unit,
    onEveryYearCategorySelected: () -> Unit,
    onDismiss: () -> Unit,

    onOneTimeCategoryDismiss: () -> Unit,
    onOneTimeCategoryDateChanged: (String) -> Unit,
    onOneTimeCategoryTimeChanged: (String) -> Unit,
    onOneTimeCategorySaveClicked: () -> Unit,

    onDaysOfWeekCategoryDismiss: () -> Unit,
    onEveryYearCategoryDismiss: () -> Unit,
) {
    AnimatedVisibility(
        visible = state != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        TaskEditingBottomSheet(
            onDismiss = onDismiss,
            block = {
                state?.let { state ->
                    CategoryEditingContent(
                        state = state,
                        onOneShotCategorySelected = onOneShotCategorySelected,
                        onOneTimeCategorySelected = onOneTimeCategorySelected,
                        onDaysOfWeekCategorySelected = onDaysOfWeekCategorySelected,
                        onEveryYearCategorySelected = onEveryYearCategorySelected
                    )
                }
            })
    }
    when (val creatingState = state?.creatingState) {
        is ICreateTaskViewModel.CategoriesDialogState.CreatingState.DaysOfWeekCategory -> {
            TaskEditingBottomSheet(
                onDismiss = onDaysOfWeekCategoryDismiss,
                block = {
                    DaysOfWeekCategoryContent(
                        state = creatingState
                    )
                })
        }
        is ICreateTaskViewModel.CategoriesDialogState.CreatingState.EveryYearCategory -> {
            TaskEditingBottomSheet(
                onDismiss = onEveryYearCategoryDismiss,
                block = {
                    EveryYearCategoryContent(
                        state = creatingState
                    )
                })
        }
        is ICreateTaskViewModel.CategoriesDialogState.CreatingState.OneTimeCategory -> {
            TaskEditingBottomSheet(
                onDismiss = onOneTimeCategoryDismiss,
                block = {
                    OneTimeCategoryContent(
                        state = creatingState,
                        onDateChanged = onOneTimeCategoryDateChanged,
                        onTimeChanged = onOneTimeCategoryTimeChanged,
                        onSaveClicked = onOneTimeCategorySaveClicked
                    )
                })
        }
        else -> Unit
    }
}

@Composable
private fun ColumnScope.CategoryEditingContent(
    state: ICreateTaskViewModel.CategoriesDialogState,
    onOneShotCategorySelected: () -> Unit,
    onOneTimeCategorySelected: () -> Unit,
    onDaysOfWeekCategorySelected: () -> Unit,
    onEveryYearCategorySelected: () -> Unit
) {
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(R.string.task_create_category_title),
        style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        ),
        color = FamilyOrganizerTheme.colors.darkGray
    )
    Spacer(modifier = Modifier.height(16.dp))

    CategorySelectingItem(
        onAction = onOneShotCategorySelected,
        text = stringResource(id = R.string.task_create_category_one_shot)
    )
    CategorySelectingItem(
        onAction = onOneTimeCategorySelected,
        text = stringResource(id = R.string.task_create_category_one_time)
    )
    CategorySelectingItem(
        onAction = onDaysOfWeekCategorySelected,
        text = stringResource(id = R.string.task_create_category_days_of_week)
    )
    CategorySelectingItem(
        onAction = onEveryYearCategorySelected,
        text = stringResource(id = R.string.task_create_category_every_year)
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ColumnScope.CategorySelectingItem(
    onAction: () -> Unit,
    text: String
) {
    BaseOutlinedButton(
        onAction = onAction,
        text = text,
        enabled = true
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ColumnScope.OneTimeCategoryContent(
    state: ICreateTaskViewModel.CategoriesDialogState.CreatingState.OneTimeCategory,
    onDateChanged: (String) -> Unit,
    onTimeChanged: (String) -> Unit,
    onSaveClicked: () -> Unit
) {
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(R.string.task_create_category_one_time_title),
        style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        ),
        color = FamilyOrganizerTheme.colors.darkGray
    )
    Spacer(modifier = Modifier.height(16.dp))

    DateTimeTextField(
        value = state.dateString,
        onValueChange = onDateChanged,
        hint = stringResource(id = R.string.task_create_category_one_time_date_hint),
        error = !state.dateValid
    )
    Spacer(modifier = Modifier.height(8.dp))
    DateTimeTextField(
        value = state.timeString,
        onValueChange = onTimeChanged,
        hint = stringResource(id = R.string.task_create_category_one_time_time_hint),
        error = !state.timeValid
    )

    Spacer(modifier = Modifier.height(16.dp))
    BaseActionButton(
        onAction = onSaveClicked,
        text = stringResource(id = R.string.task_create_category_one_save),
        enabled = state.enabled
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun DateTimeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    error: Boolean
) {
    OutlinedTextField(
        value = value,
        isError = error,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = onValueChange,
        textStyle = FamilyOrganizerTheme.textStyle.input,
        colors = outlinedTextFieldColors(),
        placeholder = { Text(text = hint) },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun DaysOfWeekCategoryContent(
    state: ICreateTaskViewModel.CategoriesDialogState.CreatingState.DaysOfWeekCategory
) {

}

@Composable
private fun EveryYearCategoryContent(
    state: ICreateTaskViewModel.CategoriesDialogState.CreatingState.EveryYearCategory
) {

}

/**
 * Notifications
 * */

@Composable
fun NotificationsEditingDialog(
    state: ICreateTaskViewModel.NotificationDialogState?,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    onChecked: (String) -> Unit
) {
    AnimatedVisibility(
        visible = state != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickableWithoutIndication(onDismiss),
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
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                .clickableWithoutIndication { }
                        ) {
                            state?.let { state ->
                                NotificationsEditingContent(
                                    state = state,
                                    onChecked = onChecked,
                                    onSave = onSave
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
private fun ColumnScope.NotificationsEditingContent(
    state: ICreateTaskViewModel.NotificationDialogState,
    onChecked: (String) -> Unit,
    onSave: () -> Unit
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Start),
        text = stringResource(id = R.string.task_create_notifications_dialog_title),
        style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
        color = FamilyOrganizerTheme.colors.darkGray
    )
    Spacer(modifier = Modifier.height(8.dp))
    state.items.forEach { notification ->
        NotificationListItem(
            notification = notification,
            onChecked = onChecked
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
    Spacer(modifier = Modifier.height(4.dp))
    Button(
        onClick = onSave,
        enabled = true,
        colors = buttonColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = stringResource(R.string.task_create_notifications_dialog_save).uppercase(),
            color = FamilyOrganizerTheme.colors.whitePrimary,
            style = FamilyOrganizerTheme.textStyle.button,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Composable
private fun NotificationListItem(
    notification: ICreateTaskViewModel.NotificationDialogState.NotificationItem,
    onChecked: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 4.dp)
    ) {
        Checkbox(
            checked = notification.checked,
            onCheckedChange = { onChecked(notification.email) },
            colors = checkBoxColors()
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = notification.name,
            style = FamilyOrganizerTheme.textStyle.body,
            color = FamilyOrganizerTheme.colors.darkGray
        )
    }
}

/**
 * Subtasks
 * */

@Composable
fun SubtasksEditingDialog(
    state: ICreateTaskViewModel.SubtasksDialogState?,

    onDeleteClicked: (String) -> Unit,
    onCreateClicked: () -> Unit,
    onSaveClicked: () -> Unit,

    onSubtaskCreatingTitleChanged: (String) -> Unit,
    onSubtaskCreatingSaveClicked: () -> Unit,

    onSubtaskDialogDismiss: () -> Unit,
    onSubtaskCreatingDialogDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = state != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickableWithoutIndication(onSubtaskDialogDismiss),
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
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                .clickableWithoutIndication { }
                        ) {
                            state?.let { state ->
                                SubtasksEditingContent(
                                    state = state,
                                    onSaveClicked = onSaveClicked,
                                    onDeleteClicked = onDeleteClicked,
                                    onCreate = onCreateClicked
                                )
                            }
                        }
                    }
                )

            }
        )
    }

    AnimatedVisibility(
        visible = state?.creatingState != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickableWithoutIndication(onSubtaskCreatingDialogDismiss),
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
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                .clickableWithoutIndication { }
                        ) {
                            state?.creatingState?.let { creatingState ->
                                SubtaskCreatingDialog(
                                    state = creatingState,
                                    onSaveClicked = onSubtaskCreatingSaveClicked,
                                    onTitleChanged = onSubtaskCreatingTitleChanged
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
private fun ColumnScope.SubtasksEditingContent(
    state: ICreateTaskViewModel.SubtasksDialogState,
    onCreate: () -> Unit,
    onSaveClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Start),
        text = stringResource(
            id = if (state.items.isNotEmpty()) {
                R.string.task_create_subtasks_dialog_title
            } else {
                R.string.task_create_subtasks_dialog_empty_title
            }
        ),
        style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
        color = FamilyOrganizerTheme.colors.darkGray
    )
    Spacer(modifier = Modifier.height(8.dp))
    state.items.forEach { subtasks ->
        SubtaskListItem(
            subtask = subtasks,
            onDeleteClicked = onDeleteClicked
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
    Spacer(modifier = Modifier.height(4.dp))

    BaseOutlinedButton(
        onAction = onCreate,
        text = stringResource(R.string.task_create_subtasks_dialog_create),
        enabled = true
    )
    Spacer(modifier = Modifier.height(4.dp))
    BaseActionButton(
        onAction = onSaveClicked,
        text = stringResource(R.string.task_create_subtasks_dialog_save),
        enabled = true
    )
}

@Composable
private fun SubtaskListItem(
    subtask: Subtask,
    onDeleteClicked: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Checkbox(
            checked = subtask.checked,
            onCheckedChange = { },
            colors = checkBoxColors()
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = subtask.text,
            style = FamilyOrganizerTheme.textStyle.body,
            color = FamilyOrganizerTheme.colors.darkGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickableWithoutIndication { onDeleteClicked(subtask.text) },
            painter = painterResource(id = R.drawable.ic_remove),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )
    }
}

@Composable
private fun SubtaskCreatingDialog(
    state: ICreateTaskViewModel.SubtasksDialogState.CreatingState,
    onTitleChanged: (String) -> Unit,
    onSaveClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 16.dp))
            .background(color = FamilyOrganizerTheme.colors.whitePrimary)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = stringResource(R.string.task_create_subtasks_creating_dialog_title),
                style = FamilyOrganizerTheme.textStyle.headline2,
                lineHeight = 26.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = state.title,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { onTitleChanged(it) },
                textStyle = FamilyOrganizerTheme.textStyle.input,
                colors = outlinedTextFieldColors(),
                placeholder = {
                    Text(
                        text = stringResource(
                            id = R.string.task_create_subtasks_creating_dialog_hint
                        )
                    )
                }
            )

            BaseActionButton(
                onAction = onSaveClicked,
                text = stringResource(R.string.task_create_subtasks_creating_dialog_save),
                enabled = state.enabled
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

/**
 * Products
 * */

@Composable
fun ProductsEditingDialog(
    state: ICreateTaskViewModel.ProductDialogState?,

    onDeleteClicked: (String) -> Unit,
    onCreateClicked: () -> Unit,
    onSaveClicked: () -> Unit,

    onProductCreatingTitleChanged: (String) -> Unit,
    onProductCreatingAmountChanged: (String) -> Unit,
    onProductCreatingMeasureChanged: (Product.Measure) -> Unit,
    onProductCreatingSaveClicked: () -> Unit,

    onProductsDialogDismiss: () -> Unit,
    onProductCreatingDialogDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = state != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickableWithoutIndication(onProductsDialogDismiss),
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
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                .clickableWithoutIndication { }
                        ) {
                            state?.let { state ->
                                ProductsEditingContent(
                                    state = state,
                                    onSaveClicked = onSaveClicked,
                                    onDeleteClicked = onDeleteClicked,
                                    onCreate = onCreateClicked
                                )
                            }
                        }
                    }
                )

            }
        )
    }

    AnimatedVisibility(
        visible = state?.creatingState != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickableWithoutIndication(onProductCreatingDialogDismiss),
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
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                .clickableWithoutIndication { }
                        ) {
                            state?.creatingState?.let { creatingState ->
                                ProductCreatingContent(
                                    state = creatingState,
                                    onTitleChanged = onProductCreatingTitleChanged,
                                    onQuantityChanged = onProductCreatingAmountChanged,
                                    onMeasureChanged = onProductCreatingMeasureChanged,
                                    onCreateClicked = onProductCreatingSaveClicked
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
private fun ColumnScope.ProductsEditingContent(
    state: ICreateTaskViewModel.ProductDialogState,
    onCreate: () -> Unit,
    onSaveClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Start),
        text = stringResource(
            id = if (state.items.isNotEmpty()) {
                R.string.task_create_products_dialog_title
            } else {
                R.string.task_create_products_dialog_empty_title
            }
        ),
        style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
        color = FamilyOrganizerTheme.colors.darkGray
    )
    Spacer(modifier = Modifier.height(8.dp))
    state.items.forEach { product ->
        ProductsListItem(
            product = product,
            onDeleteClicked = onDeleteClicked
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
    Spacer(modifier = Modifier.height(4.dp))

    BaseOutlinedButton(
        onAction = onCreate,
        text = stringResource(R.string.task_create_products_dialog_create),
        enabled = true
    )
    Spacer(modifier = Modifier.height(4.dp))
    BaseActionButton(
        onAction = onSaveClicked,
        text = stringResource(R.string.task_create_products_dialog_save),
        enabled = true
    )
}

@Composable
private fun ProductsListItem(
    product: TaskProduct,
    onDeleteClicked: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = product.checked,
            onCheckedChange = {},
            colors = checkBoxColors()
        )
        Spacer(modifier = Modifier.width(4.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.title,
                style = FamilyOrganizerTheme.textStyle.body,
                color = FamilyOrganizerTheme.colors.darkGray,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))

            product.amount?.let { amount ->
                product.measure?.let { measure ->
                    Text(
                        text = "$amount ${getMeasureString(quantity = amount, measure = measure)}",
                        style = FamilyOrganizerTheme.textStyle.label.copy(
                            fontWeight = FontWeight.Light
                        ),
                        color = FamilyOrganizerTheme.colors.darkClay,
                        maxLines = 1
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickableWithoutIndication { onDeleteClicked(product.title) },
            painter = painterResource(id = R.drawable.ic_remove),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )
    }
}

@Composable
private fun ColumnScope.ProductCreatingContent(
    state: ICreateTaskViewModel.ProductDialogState.CreatingState,
    onTitleChanged: (String) -> Unit,
    onQuantityChanged: (String) -> Unit,
    onMeasureChanged: (Product.Measure) -> Unit,
    onCreateClicked: () -> Unit
) {

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(
            id = R.string.bottom_sheet_adding_title
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
        value = state.title,
        hint = stringResource(id = R.string.bottom_sheet_adding_hint_title),
        onValueChange = onTitleChanged
    )
    Spacer(modifier = Modifier.height(16.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
        AddingBottomSheetTextInput(
            modifier = Modifier.weight(1f),
            value = state.amount?.toString().orEmpty(),
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
                value = getStringForMeasure(
                    state.measure ?: Product.Measure.KILOGRAM
                ),
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
                                text = getStringForMeasure(measure = selectionOption),
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
    Spacer(modifier = Modifier.height(40.dp))

    Button(
        onClick = onCreateClicked,
        enabled = state.enabled,
        colors = buttonColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = stringResource(R.string.bottom_sheet_adding_button).uppercase(),
            color = FamilyOrganizerTheme.colors.whitePrimary,
            style = FamilyOrganizerTheme.textStyle.button,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

/**
 * Utils
 * */

@Composable
private fun TaskEditingBottomSheet(
    block: @Composable ColumnScope.() -> Unit,
    onDismiss: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickableWithoutIndication(onDismiss),
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
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .clickableWithoutIndication { },
                        content = block
                    )
                }
            )

        }
    )
}
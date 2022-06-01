package com.badger.familyorgfe.features.appjourney.tasks.createtask

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.data.model.Subtask
import com.badger.familyorgfe.data.model.TaskCategory
import com.badger.familyorgfe.data.model.TaskProduct
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.ProductListItem
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.SubtaskListItem
import com.badger.familyorgfe.features.appjourney.tasks.taskdetails.getCategoryDescription
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ICreateTaskViewModel = hiltViewModel<CreateTaskViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val categoriesState by viewModel.categoriesDialogState.collectAsState()
    val notificationsState by viewModel.notificationsDialogState.collectAsState()
    val subtasksState by viewModel.subtasksDialogState.collectAsState()
    val productsState by viewModel.productsDialogState.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Toolbar(
                doneEnabled = state.doneEnabled,
                onBackClicked = { navController.popBackStack() },
                onDoneClicked = {
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.Ordinal.OnDoneClicked
                    )
                },
                creating = state.creating
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            MainInformationTitle()
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            TitleTextField(
                value = state.title,
                valid = state.titleValid,
                onValueChanged = { title ->
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.Ordinal.OnTitleValueChanged(title)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            DescriptionTextField(
                value = state.description,
                valid = state.descriptionValid,
                onValueChanged = { description ->
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.Ordinal.OnDescriptionValueChanged(description)
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            CategoryBlock(
                category = state.category,
                onEditClicked = {
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.Ordinal.OpenCategories(state.category)
                    )
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            NotificationsBlock(
                notifications = state.notifications,
                localNames = emptyList(),
                onEditClicked = {
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.Ordinal.OpenNotifications(state.notifications)
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            SubtasksBlock(
                subtasks = state.subtasks,
                onCheckedChanged = { id, checked -> },
                onEditClicked = {
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.Ordinal.OpenSubtasks(state.subtasks)
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ProductsBlock(
                products = state.products,
                onCheckedChanged = { id, checked -> },
                onEditClicked = {
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.Ordinal.OpenProducts(state.products)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    CategoryEditingDialog(
        state = categoriesState,
        onOneShotCategorySelected = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Categories.OnOneShotCategoryClicked)
        },
        onOneTimeCategorySelected = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Categories.OnOneTimeCategoryClicked)
        },
        onDaysOfWeekCategorySelected = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Categories.OnDaysOfWeekCategoryClicked)
        },
        onEveryYearCategorySelected = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Categories.OnEveryYearCategoryClicked)
        },
        onDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Categories.Dismiss)
        },
        onOneTimeCategoryDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingOneTimeCategory.Dismiss)
        },
        onDaysOfWeekCategoryDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingDaysOfWeekCategory.Dismiss)
        },
        onEveryYearCategoryDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingEveryYearCategory.Dismiss)
        },
        onOneTimeCategoryDateChanged = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingOneTimeCategory.OnDateChanged(it))
        },
        onOneTimeCategoryTimeChanged = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingOneTimeCategory.OnTimeChanged(it))
        },
        onOneTimeCategorySaveClicked = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingOneTimeCategory.Save)
        },
        onEveryYearCategoryDateChanged = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingEveryYearCategory.OnDateChanged(it))
        },
        onEveryYearCategoryTimeChanged = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingEveryYearCategory.OnTimeChanged(it))
        },
        onEveryYearCategorySaveClicked = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingEveryYearCategory.Save)
        }
    )

    NotificationsEditingDialog(
        state = notificationsState,
        onSave = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Notifications.Save)
        },
        onDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Notifications.Dismiss)
        },
        onChecked = { email ->
            viewModel.onEvent(ICreateTaskViewModel.Event.Notifications.Checked(email))
        }
    )

    SubtasksEditingDialog(
        state = subtasksState,
        onDeleteClicked = { title ->
            viewModel.onEvent(ICreateTaskViewModel.Event.Subtasks.Delete(title))
        },
        onCreateClicked = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Subtasks.Create)
        },
        onSaveClicked = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Subtasks.Save)
        },
        onSubtaskDialogDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Subtasks.Dismiss)
        },
        onSubtaskCreatingTitleChanged = { text ->
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingSubtask.OnTitleChanged(text))
        },
        onSubtaskCreatingSaveClicked = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingSubtask.Save)
        },
        onSubtaskCreatingDialogDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingSubtask.Dismiss)
        })

    ProductsEditingDialog(
        state = productsState,
        onDeleteClicked = { title ->
            viewModel.onEvent(ICreateTaskViewModel.Event.Products.Delete(title))
        },
        onCreateClicked = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Products.Create)
        },
        onSaveClicked = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Products.Save)
        },
        onProductsDialogDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.Products.Dismiss)
        },
        onProductCreatingTitleChanged = { text ->
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingProducts.OnTitleChanged(text))
        },
        onProductCreatingAmountChanged = { amount ->
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingProducts.OnAmountChanged(amount))
        },
        onProductCreatingMeasureChanged = { measure ->
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingProducts.OnMeasureChanged(measure))
        },
        onProductCreatingSaveClicked = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingProducts.Save)
        },
        onProductCreatingDialogDismiss = {
            viewModel.onEvent(ICreateTaskViewModel.Event.CreatingProducts.Dismiss)
        }
    )
}

@Composable
private fun Toolbar(
    onBackClicked: () -> Unit,
    onDoneClicked: () -> Unit,
    creating: Boolean,
    doneEnabled: Boolean
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
            text = stringResource(
                id = if (creating) {
                    R.string.task_create_title
                } else {
                    R.string.task_edit_title
                }
            ),
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

/**
 * Main information
 * */

@Composable
private fun MainInformationTitle() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = stringResource(id = R.string.task_main_info_title),
        style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
        color = FamilyOrganizerTheme.colors.darkGray
    )
}

@Composable
private fun TitleTextField(
    value: String,
    valid: Boolean,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        value = value,
        isError = !valid,
        singleLine = true,
        onValueChange = onValueChanged,
        textStyle = FamilyOrganizerTheme.textStyle.input,
        keyboardOptions = KeyboardOptions.Default,
        colors = outlinedTextFieldColors(),
        placeholder = {
            Text(text = stringResource(id = R.string.task_create_title_hint))
        }
    )
}

@Composable
private fun DescriptionTextField(
    value: String,
    valid: Boolean,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        value = value,
        isError = !valid,
        onValueChange = onValueChanged,
        textStyle = FamilyOrganizerTheme.textStyle.input,
        keyboardOptions = KeyboardOptions.Default,
        colors = outlinedTextFieldColors(),
        placeholder = {
            Text(text = stringResource(id = R.string.task_create_description_hint))
        }
    )
}

@Composable
private fun CategoryBlock(
    category: TaskCategory,
    onEditClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = category.imageResId),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            text = getCategoryDescription(category = category),
            style = FamilyOrganizerTheme.textStyle.body,
            color = FamilyOrganizerTheme.colors.darkGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        TaskEditIcon(onEditClicked = onEditClicked)
    }
}

@Composable
private fun NotificationsBlock(
    notifications: List<String>,
    localNames: List<LocalName>,
    onEditClicked: () -> Unit
) {
    val notificationLocalNames = notifications.map { email ->
        localNames.find { it.email == email } ?: email
    }.joinToString(separator = ", ", postfix = ".")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {
        if (notifications.isNotEmpty()) {
            Icon(
                modifier = Modifier.size(size = 24.dp),
                painter = painterResource(id = R.drawable.ic_notification_on),
                contentDescription = null,
                tint = FamilyOrganizerTheme.colors.blackPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = "${stringResource(id = R.string.tasks_notifications_on)} $notificationLocalNames",
                style = FamilyOrganizerTheme.textStyle.body,
                color = FamilyOrganizerTheme.colors.blackPrimary
            )
        } else {
            Icon(
                modifier = Modifier.size(size = 24.dp),
                painter = painterResource(id = R.drawable.ic_notifications_off),
                contentDescription = null,
                tint = FamilyOrganizerTheme.colors.blackPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.tasks_notifications_off),
                style = FamilyOrganizerTheme.textStyle.body,
                color = FamilyOrganizerTheme.colors.blackPrimary
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        TaskEditIcon(onEditClicked = onEditClicked)
    }
}

/**
 * Attachments
 * */

@Composable
private fun SubtasksBlock(
    subtasks: List<Subtask>,
    onCheckedChanged: (Long, Boolean) -> Unit,
    onEditClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(
                    id = if (subtasks.isNotEmpty()) {
                        R.string.task_desc_subtasks_is_not_empty_title
                    } else {
                        R.string.task_desc_subtasks_is_empty_title
                    }
                ),
                style = FamilyOrganizerTheme.textStyle.body.copy(fontWeight = FontWeight.Medium),
                color = FamilyOrganizerTheme.colors.blackPrimary,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(8.dp))
            TaskEditIcon(onEditClicked = onEditClicked)
        }

        subtasks.forEach { item ->
            SubtaskListItem(
                task = item,
                onCheckedChanged = { checked -> onCheckedChanged(item.id, checked) }
            )
        }
    }
}

@Composable
private fun ProductsBlock(
    products: List<TaskProduct>,
    onCheckedChanged: (Long, Boolean) -> Unit,
    onEditClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(
                    id = if (products.isNotEmpty()) {
                        R.string.task_desc_products_is_not_empty_title
                    } else {
                        R.string.task_desc_products_is_empty_title
                    }
                ),
                style = FamilyOrganizerTheme.textStyle.body.copy(fontWeight = FontWeight.Medium),
                color = FamilyOrganizerTheme.colors.blackPrimary,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(8.dp))
            TaskEditIcon(onEditClicked = onEditClicked)
        }
        products.forEach { item ->
            ProductListItem(
                product = item,
                onCheckedChanged = { checked -> onCheckedChanged(item.id, checked) }
            )
        }
    }
}

/**
 * Utils
 * */

@Composable
private fun TaskEditIcon(
    onEditClicked: () -> Unit
) {
    Icon(
        modifier = Modifier
            .size(24.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    color = FamilyOrganizerTheme.colors.primary
                ),
                onClick = onEditClicked
            ),
        painter = painterResource(id = R.drawable.ic_edit_pencil),
        contentDescription = null,
        tint = FamilyOrganizerTheme.colors.primary
    )
}
package com.badger.familyorgfe.features.appjourney.tasks.taskdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.*
import com.badger.familyorgfe.features.appjourney.bottomnavigation.TasksNavigationType
import com.badger.familyorgfe.features.appjourney.products.fridge.fridgeitem.getMeasureString
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.style.checkBoxColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun TaskDetailsScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: ITaskDetailsViewModel = hiltViewModel<TaskDetailsViewModel>()
) {
    val nullableFamilyTask by viewModel.familyTask.collectAsState()
    val localNames by viewModel.localNames.collectAsState()

    nullableFamilyTask?.let { familyTask ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Toolbar(
                    status = familyTask.status,
                    onEditClicked = {
                        navController.navigate(TasksNavigationType.CREATE_TASK_SCREEN.route)
                    },
                    onBackClicked = { navController.popBackStack() }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = familyTask.title,
                    style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = FamilyOrganizerTheme.colors.blackPrimary,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = familyTask.desc,
                    style = FamilyOrganizerTheme.textStyle.body,
                    color = FamilyOrganizerTheme.colors.darkGray
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                CategoryBlock(category = familyTask.category)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                NotificationsBlock(
                    notifications = familyTask.notificationEmails,
                    localNames = localNames
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                SubtasksBlock(
                    subtasks = familyTask.subtasks,
                    onCheckedChanged = { id, checked ->
                        viewModel.onEvent(
                            ITaskDetailsViewModel.Event.OnSubtaskChecked(id, checked)
                        )
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                ProductsBlock(
                    products = familyTask.products,
                    onCheckedChanged = { id, checked ->
                        viewModel.onEvent(
                            ITaskDetailsViewModel.Event.OnProductChecked(id, checked)
                        )
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}

@Composable
private fun Toolbar(
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    status: TaskStatus
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
            text = stringResource(id = status.textResourceId),
            style = FamilyOrganizerTheme.textStyle.headline2,
            color = FamilyOrganizerTheme.colors.blackPrimary
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onEditClicked() },
            painter = painterResource(id = R.drawable.ic_edit_pencil),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun LazyItemScope.CategoryBlock(category: TaskCategory) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
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
            text = "js`dklfjlsdfjldsjflsjdflksdjjs`dklfjlsdfjldsjflsjdflksdjjs`dklfjlsdfjldsjflsjdflksdjjs`dklfjlsdfjldsjflsjdflksdjjs`dklfjlsdfjldsjflsjdflksdjjs`dklfjlsdfjldsjflsjdflksdj",
            style = FamilyOrganizerTheme.textStyle.body,
            color = FamilyOrganizerTheme.colors.darkGray
        )
    }
}

@Composable
private fun LazyItemScope.NotificationsBlock(
    notifications: List<String>,
    localNames: List<LocalName>
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
                text = stringResource(id = R.string.tasks_notifications_off),
                style = FamilyOrganizerTheme.textStyle.body,
                color = FamilyOrganizerTheme.colors.blackPrimary
            )
        }
    }
}

@Composable
private fun SubtasksBlock(
    subtasks: List<Subtask>,
    onCheckedChanged: (Long, Boolean) -> Unit
) {
    if (subtasks.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.task_desc_subtasks_title),
                style = FamilyOrganizerTheme.textStyle.body.copy(fontWeight = FontWeight.Medium),
                color = FamilyOrganizerTheme.colors.blackPrimary,
                maxLines = 1
            )
            subtasks.forEach { item ->
                SubtaskListItem(
                    task = item,
                    onCheckedChanged = { checked -> onCheckedChanged(item.id, checked) }
                )
            }
        }
    }
}

@Composable
private fun SubtaskListItem(
    task: Subtask,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 4.dp)
    ) {
        Checkbox(
            checked = task.checked,
            onCheckedChange = onCheckedChanged,
            colors = checkBoxColors()
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = task.text,
            style = FamilyOrganizerTheme.textStyle.body,
            color = if (task.checked) {
                FamilyOrganizerTheme.colors.darkClay
            } else {
                FamilyOrganizerTheme.colors.darkGray
            }
        )
    }
}

@Composable
private fun LazyItemScope.ProductsBlock(
    products: List<TaskProduct>,
    onCheckedChanged: (Long, Boolean) -> Unit
) {
    if (products.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.task_desc_products_title),
                style = FamilyOrganizerTheme.textStyle.body.copy(fontWeight = FontWeight.Medium),
                color = FamilyOrganizerTheme.colors.blackPrimary,
                maxLines = 1
            )
            products.forEach { item ->
                ProductListItem(
                    product = item,
                    onCheckedChanged = { checked -> onCheckedChanged(item.id, checked) }
                )
            }
        }
    }
}

@Composable
private fun ProductListItem(
    product: TaskProduct,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 4.dp)
    ) {
        Checkbox(
            checked = product.checked,
            onCheckedChange = onCheckedChanged,
            colors = checkBoxColors()
        )
        Spacer(modifier = Modifier.width(4.dp))

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = product.title,
                style = FamilyOrganizerTheme.textStyle.body,
                color = if (product.checked) {
                    FamilyOrganizerTheme.colors.darkClay
                } else {
                    FamilyOrganizerTheme.colors.darkGray
                },
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = getMeasureString(quantity = product.amount, measure = product.measure),
                style = FamilyOrganizerTheme.textStyle.label.copy(fontWeight = FontWeight.Light),
                color = FamilyOrganizerTheme.colors.darkClay,
                maxLines = 1
            )
        }
    }
}
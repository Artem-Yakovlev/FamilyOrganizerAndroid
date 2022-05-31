package com.badger.familyorgfe.features.appjourney.tasks.createtask

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.Subtask
import com.badger.familyorgfe.ext.clickableWithoutIndication
import com.badger.familyorgfe.ui.elements.BaseActionButton
import com.badger.familyorgfe.ui.elements.BaseTextButton
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.checkBoxColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

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
        text = stringResource(id = R.string.task_create_subtasks_dialog_title),
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

    BaseTextButton(
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

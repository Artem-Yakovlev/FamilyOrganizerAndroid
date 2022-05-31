package com.badger.familyorgfe.features.appjourney.tasks.createtask

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ext.clickableWithoutIndication
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.checkBoxColors
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

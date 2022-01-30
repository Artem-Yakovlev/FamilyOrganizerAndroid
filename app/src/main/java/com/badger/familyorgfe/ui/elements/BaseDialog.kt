package com.badger.familyorgfe.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun BaseDialog(
    titleText: String,
    descriptionText: String,
    dismissText: String,
    actionText: String,
    onDismissClicked: () -> Unit,
    onActionClicked: () -> Unit,
    onDismissRequest: () -> Unit = onDismissClicked
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center,
            content = {
                DialogContent(
                    titleText = titleText,
                    descriptionText = descriptionText,
                    dismissText = dismissText,
                    actionText = actionText,
                    onDismissClicked = onDismissClicked,
                    onActionClicked = onActionClicked
                )
            }
        )
    }
}

@Composable
private fun DialogContent(
    titleText: String,
    descriptionText: String,
    dismissText: String,
    actionText: String,
    onDismissClicked: () -> Unit,
    onActionClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 16.dp))
            .background(color = FamilyOrganizerTheme.colors.whitePrimary)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {

        Text(
            text = titleText,
            style = FamilyOrganizerTheme.textStyle.headline3.copy(fontSize = 20.sp),
            color = FamilyOrganizerTheme.colors.blackPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = descriptionText,
            style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
            color = FamilyOrganizerTheme.colors.darkGray
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onDismissClicked) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = dismissText.uppercase(),
                    style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
                    color = FamilyOrganizerTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            TextButton(onClick = onActionClicked) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = actionText.uppercase(),
                    style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
                    color = FamilyOrganizerTheme.colors.primary
                )
            }
        }
    }
}
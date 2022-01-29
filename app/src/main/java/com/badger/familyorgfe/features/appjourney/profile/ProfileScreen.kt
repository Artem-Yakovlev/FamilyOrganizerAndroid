package com.badger.familyorgfe.features.appjourney.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.elements.BaseDialog
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun ProfileScreen(
    modifier: Modifier,
    viewModel: IProfileViewModel = hiltViewModel<ProfileViewModel>()
) {
    val showLogoutDialog by viewModel.showLogoutDialog.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Toolbar(onLogoutClicked = { viewModel.onEvent(IProfileViewModel.Event.OnLogoutClick) })

        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            val user by viewModel.mainUser.collectAsState()

            ProfileItem(
                title = stringResource(id = R.string.profile_name_title),
                value = user.name
            )

            ProfileItem(
                title = stringResource(id = R.string.profile_login_title),
                value = user.email
            )
        }

        if (showLogoutDialog) {
            BaseDialog(
                titleText = stringResource(id = R.string.profile_logout_title),
                descriptionText = stringResource(id = R.string.profile_logout_description),
                dismissText = stringResource(id = R.string.profile_logout_dismiss),
                actionText = stringResource(id = R.string.profile_logout_action),
                onActionClicked = { viewModel.onEvent(IProfileViewModel.Event.OnLogoutAccepted) },
                onDismissClicked = { viewModel.onEvent(IProfileViewModel.Event.OnLogoutDismiss) }
            )
        }
    }
}

@Composable
private fun Toolbar(onLogoutClicked: () -> Unit) {
    BaseToolbar {
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.profile_toolbar_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            color = FamilyOrganizerTheme.colors.blackPrimary
        )

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = rememberRipple(bounded = false),
                    interactionSource = remember { MutableInteractionSource() }
                ) { onLogoutClicked() },
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun ProfileItem(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = title,
                style = FamilyOrganizerTheme.textStyle.body,
                color = FamilyOrganizerTheme.colors.darkClay
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = FamilyOrganizerTheme.textStyle.subtitle2.copy(fontWeight = FontWeight.Bold),
                color = FamilyOrganizerTheme.colors.blackPrimary
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}
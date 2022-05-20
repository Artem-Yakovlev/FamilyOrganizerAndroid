package com.badger.familyorgfe.features.appjourney.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.badger.familyorgfe.R
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import com.badger.familyorgfe.ui.elements.BaseDialog
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.ui.theme.StatusAtHomeColor
import com.badger.familyorgfe.ui.theme.WhitePrimary

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

        val mainFamilyMember by viewModel.mainUser.collectAsState()
        val members by viewModel.members.collectAsState()

        MainUserItem(
            familyMember = mainFamilyMember
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(members) { item ->
                FamilyMemberItem(familyMember = item)
                Spacer(modifier = Modifier.height(16.dp))
            }
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
private fun MainUserItem(
    familyMember: FamilyMember
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(bottomEnd = 16.dp),
            elevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
            ) {

                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f)
                        .padding(vertical = 16.dp)
                ) {
                    Box(modifier = Modifier.size(57.dp)) {
                        Image(
                            modifier = Modifier
                                .size(57.dp)
                                .clip(CircleShape),
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                        OnlineCircle(familyMember.online)
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                    ) {

                        Text(
                            text = familyMember.name,
                            style = FamilyOrganizerTheme.textStyle.headline3.copy(fontSize = 18.sp),
                            color = FamilyOrganizerTheme.colors.blackPrimary,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(id = familyMember.status.stringResource),
                            style = FamilyOrganizerTheme.textStyle.body,
                            color = familyMember.status.color,
                            maxLines = 1
                        )
                    }
                }

                Divider(
                    color = FamilyOrganizerTheme.colors.lightGray,
                    modifier = Modifier
                        .height(90.dp)
                        .width(1.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(CenterVertically),
                    painter = painterResource(id = R.drawable.ic_bottom_navigation_account),
                    contentDescription = null,
                    tint = FamilyOrganizerTheme.colors.darkClay
                )
            }
        }
    }
}

@Composable
private fun FamilyMemberItem(
    familyMember: FamilyMember
) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {

        Box(modifier = Modifier.size(57.dp)) {
            Image(
                modifier = Modifier
                    .size(57.dp)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            OnlineCircle(familyMember.online)
        }
        Spacer(modifier = Modifier.width(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {

                    Text(
                        text = familyMember.name,
                        style = FamilyOrganizerTheme.textStyle.headline3.copy(fontSize = 18.sp),
                        color = FamilyOrganizerTheme.colors.blackPrimary,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(id = familyMember.status.stringResource),
                        style = FamilyOrganizerTheme.textStyle.body,
                        color = familyMember.status.color,
                        maxLines = 1
                    )
                }
                Divider(
                    color = FamilyOrganizerTheme.colors.lightGray,
                    modifier = Modifier
                        .height(62.dp)
                        .width(1.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(CenterVertically),
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                    tint = FamilyOrganizerTheme.colors.darkClay
                )

                Spacer(modifier = Modifier.width(16.dp))
            }

        }
    }
}

@Composable
private fun BoxScope.OnlineCircle(online: Boolean) {
    Box(
        modifier = Modifier
            .size(15.dp)
            .clip(CircleShape)
            .background(WhitePrimary)
            .align(Alignment.BottomEnd)
            .padding(1.dp)
    ) {
        val onlineColor = if (online) {
            StatusAtHomeColor
        } else {
            FamilyOrganizerTheme.colors.disabled
        }
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(onlineColor)
                .align(Alignment.Center)
        )
    }
}

private const val imageUrl =
    "https://st.depositphotos.com/1144472/2003/i/600/depositphotos_20030237-stock-photo-cheerful-young-man-over-white.jpg"
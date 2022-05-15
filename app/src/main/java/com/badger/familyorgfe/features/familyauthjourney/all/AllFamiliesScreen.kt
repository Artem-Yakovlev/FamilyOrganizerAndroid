package com.badger.familyorgfe.features.familyauthjourney.all

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.Family
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun AllFamiliesScreen(viewModel: IAllFamiliesViewModel = hiltViewModel<AllFamiliesViewModel>()) {

    LaunchedEffect(Unit) {
        viewModel.onEvent(IAllFamiliesViewModel.Event.Init)
    }
    val familiesAndInvites by viewModel.familiesAndInvites.collectAsState()
    val isCreating by viewModel.isCreating.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        FamiliesAndInvitesListing(
            familiesAndInvites = familiesAndInvites,
            onAcceptClick = { viewModel.onEvent(IAllFamiliesViewModel.Event.AcceptInvite(it)) },
            onDeclineClick = { viewModel.onEvent(IAllFamiliesViewModel.Event.DeclineInvite(it)) }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun FamiliesAndInvitesListing(
    familiesAndInvites: FamiliesAndInvites,
    onAcceptClick: (Long) -> Unit,
    onDeclineClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(24.dp))
        if (familiesAndInvites.families.isNotEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.family_auth_families),
                style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
                color = FamilyOrganizerTheme.colors.darkClay
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                familiesAndInvites.families.forEach { item ->
                    FamilyItem(family = item)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
        }

        if (familiesAndInvites.invites.isNotEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.family_auth_invites),
                style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
                color = FamilyOrganizerTheme.colors.darkClay
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                familiesAndInvites.invites.forEach { item ->
                    InviteItem(
                        family = item,
                        onAcceptClick = onAcceptClick,
                        onDeclineClick = onDeclineClick
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun FamilyItem(family: Family) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),

        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = family.name,
                style = FamilyOrganizerTheme.textStyle.headline3.copy(fontSize = 18.sp),
                color = FamilyOrganizerTheme.colors.blackPrimary
            )


            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { },
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = null,
                tint = FamilyOrganizerTheme.colors.blackPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
private fun InviteItem(
    family: Family,
    onAcceptClick: (Long) -> Unit,
    onDeclineClick: (Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = family.name,
                style = FamilyOrganizerTheme.textStyle.headline3.copy(fontSize = 18.sp),
                color = FamilyOrganizerTheme.colors.blackPrimary
            )

            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onAcceptClick(family.id) },
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = null,
                tint = FamilyOrganizerTheme.colors.blackPrimary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onDeclineClick(family.id) },
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
package com.badger.familyorgfe.features.familyauthjourney.all

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.Family
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedButtonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
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
            onDeclineClick = { viewModel.onEvent(IAllFamiliesViewModel.Event.DeclineInvite(it)) },
            onOpenClick = { viewModel.onEvent(IAllFamiliesViewModel.Event.OpenFamily(it)) },
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = !isCreating,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, FamilyOrganizerTheme.colors.primary),
            onClick = { viewModel.onEvent(IAllFamiliesViewModel.Event.StartFamilyCreating) },
            colors = outlinedButtonColors(),
        ) {
            Text(
                text = stringResource(R.string.family_auth_create).uppercase(),
                color = FamilyOrganizerTheme.colors.primary,
                style = FamilyOrganizerTheme.textStyle.button,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }

    if (isCreating) {
        val onDismissClicked = { viewModel.onEvent(IAllFamiliesViewModel.Event.OnDismissDialog) }
        val familyName by viewModel.name.collectAsState()
        val createEnabled by viewModel.createEnabled.collectAsState()

        Dialog(
            onDismissRequest = onDismissClicked
        ) {
            Box(
                contentAlignment = Alignment.Center,
                content = {
                    CreateFamilyDialog(
                        name = familyName,
                        createEnabled = createEnabled,
                        onTextChanged = {
                            viewModel.onEvent((IAllFamiliesViewModel.Event.OnNameChanged(it)))
                        },
                        onCreateClicked = {
                            viewModel.onEvent(IAllFamiliesViewModel.Event.CreateFamily(familyName))
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun CreateFamilyDialog(
    name: String,
    createEnabled: Boolean,
    onTextChanged: (String) -> Unit,
    onCreateClicked: (String) -> Unit
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
                text = stringResource(R.string.family_auth_dialog_title),
                style = FamilyOrganizerTheme.textStyle.headline2,
                lineHeight = 26.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = stringResource(R.string.family_auth_dialog_desc),
                style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                    fontSize = 14.sp,
                    color = FamilyOrganizerTheme.colors.darkClay
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = name,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { onTextChanged(it) },
                textStyle = FamilyOrganizerTheme.textStyle.input,
                colors = outlinedTextFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Button(
                onClick = { onCreateClicked(name) },
                enabled = createEnabled,
                colors = buttonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = stringResource(R.string.family_auth_dialog_create).uppercase(),
                    color = FamilyOrganizerTheme.colors.whitePrimary,
                    style = FamilyOrganizerTheme.textStyle.button,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ColumnScope.FamiliesAndInvitesListing(
    familiesAndInvites: FamiliesAndInvites,
    onAcceptClick: (Long) -> Unit,
    onDeclineClick: (Long) -> Unit,
    onOpenClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
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
                    FamilyItem(family = item, onOpenClick = onOpenClick)
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
private fun FamilyItem(family: Family, onOpenClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    indication = rememberRipple(bounded = true),
                    interactionSource = remember { MutableInteractionSource() }
                ) { onOpenClick(family.id) },
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
                        indication = rememberRipple(bounded = false),
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
                        indication = rememberRipple(bounded = false),
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
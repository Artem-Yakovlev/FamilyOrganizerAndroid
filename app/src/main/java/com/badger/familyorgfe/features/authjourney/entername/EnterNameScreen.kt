package com.badger.familyorgfe.features.authjourney.entername

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.features.authjourney.entername.IEnterNameViewModel.Event
import com.badger.familyorgfe.ui.elements.FullScreenLoading
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun EnterNameScreen(
    modifier: Modifier,
    viewModel: IEnterNameViewModel = hiltViewModel<EnterNameViewModel>()
) {

    LaunchedEffect(Unit) {
        viewModel.onEvent(Event.Init)
    }

    val loading by viewModel.isLoading.collectAsState()
    val screen = @Composable {
        Screen(
            modifier = modifier,
            viewModel = viewModel
        )
    }
    if (loading) {
        FullScreenLoading(
            modifier = modifier.fillMaxSize(),
            content = screen
        )
    } else {
        screen()
    }
}

@Composable
fun Screen(
    modifier: Modifier,
    viewModel: IEnterNameViewModel
) {
    val name by viewModel.name.collectAsState()

    val continueEnabled by viewModel.continueEnabled.collectAsState()

    Column(
        modifier = modifier
            .background(FamilyOrganizerTheme.colors.whitePrimary)
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(R.string.enter_name_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            lineHeight = 26.sp,
            modifier = Modifier.padding(top = 50.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.onEvent(Event.NameUpdate(it)) },
            textStyle = FamilyOrganizerTheme.textStyle.input,
            colors = outlinedTextFieldColors(),
            placeholder = { Text(text = stringResource(R.string.enter_name_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        )

        Button(
            onClick = { viewModel.onEvent(Event.ContinueClick(name)) },
            enabled = continueEnabled,
            colors = buttonColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {

            Text(
                text = stringResource(R.string.next_text).uppercase(),
                color = FamilyOrganizerTheme.colors.whitePrimary,
                style = FamilyOrganizerTheme.textStyle.button,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        TextButton(
            onClick = { viewModel.onEvent(Event.SkipClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {

            Text(
                text = stringResource(R.string.enter_name_skip).uppercase(),
                color = FamilyOrganizerTheme.colors.primary,
                style = FamilyOrganizerTheme.textStyle.button,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}
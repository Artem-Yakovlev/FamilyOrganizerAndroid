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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun CreateTaskScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: ICreateTaskViewModel = hiltViewModel<CreateTaskViewModel>()
) {
    val doneEnabled by viewModel.doneEnabled.collectAsState()
    val creating by viewModel.creating.collectAsState()

    val titleValue by viewModel.titleValue.collectAsState()
    val titleError by viewModel.titleError.collectAsState()

    val descriptionValue by viewModel.descriptionValue.collectAsState()
    val descriptionError by viewModel.descriptionError.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Toolbar(
                doneEnabled = doneEnabled,
                onBackClicked = { navController.popBackStack() },
                onDoneClicked = { viewModel.onEvent(ICreateTaskViewModel.Event.OnDoneClicked) },
                creating = creating
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            TitleTextField(
                value = titleValue,
                error = titleError,
                onValueChanged = { title ->
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.OnTitleValueChanged(title)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            DescriptionTextField(
                value = descriptionValue,
                error = descriptionError,
                onValueChanged = { description ->
                    viewModel.onEvent(
                        ICreateTaskViewModel.Event.OnDescriptionValueChanged(description)
                    )
                }
            )
        }
    }
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

@Composable
private fun TitleTextField(
    value: String,
    error: String?,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        value = value,
        isError = error != null,
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
    error: String?,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        value = value,
        isError = error != null,
        onValueChange = onValueChanged,
        textStyle = FamilyOrganizerTheme.textStyle.input,
        keyboardOptions = KeyboardOptions.Default,
        colors = outlinedTextFieldColors(),
        placeholder = {
            Text(text = stringResource(id = R.string.task_create_description_hint))
        }
    )
}
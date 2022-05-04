package com.badger.familyorgfe.features.authjourney.mail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.elements.FullScreenLoading
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MailScreen(
    modifier: Modifier,
    viewModel: IMailViewModel = hiltViewModel<MailViewModel>(),
    onEmailSent: (email: String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onEmailSentAction.collectLatest { email ->
            onEmailSent(email)
        }
    }

    val loading by viewModel.isLoading.collectAsState()
    val screen = @Composable { Screen(viewModel = viewModel) }
    if (loading) {
        FullScreenLoading(
            modifier = modifier.fillMaxSize(),
            content = screen
        )
    } else {
        Screen(
            viewModel = viewModel
        )
    }
}

@Composable
private fun Screen(
    viewModel: IMailViewModel
) {
    val mail by viewModel.mail.collectAsState()
    val continueEnabled by viewModel.continueEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FamilyOrganizerTheme.colors.whitePrimary)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(R.string.enter_mail_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            lineHeight = 26.sp,
            modifier = Modifier.padding(top = 64.dp)
        )

        Text(
            text = stringResource(R.string.enter_mail_subtitle),
            style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                fontSize = 14.sp,
                color = FamilyOrganizerTheme.colors.darkClay
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        OutlinedTextField(
            value = mail,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { viewModel.onEvent(IMailViewModel.Event.MailUpdate(it)) },
            textStyle = FamilyOrganizerTheme.textStyle.input,
            colors = outlinedTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Button(
            onClick = { viewModel.onEvent(IMailViewModel.Event.ContinueClick(mail)) },
            enabled = continueEnabled,
            colors = buttonColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {

            Text(
                text = stringResource(R.string.continue_text).uppercase(),
                color = FamilyOrganizerTheme.colors.whitePrimary,
                style = FamilyOrganizerTheme.textStyle.button,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}
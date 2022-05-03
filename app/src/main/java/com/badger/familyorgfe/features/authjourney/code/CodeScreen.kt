package com.badger.familyorgfe.features.authjourney.code

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.features.authjourney.code.ICodeViewModel.Event
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun CodeScreen(
    modifier: Modifier,
    viewModel: ICodeViewModel = hiltViewModel<CodeViewModel>()
) {

    val code by viewModel.code.collectAsState()

    val continueEnabled by viewModel.continueEnabled.collectAsState()

    val resendCodeEnabled by viewModel.resendCodeEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FamilyOrganizerTheme.colors.whitePrimary),
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_back),
                tint = FamilyOrganizerTheme.colors.primary,
                contentDescription = "",
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = false,
                            color = FamilyOrganizerTheme.colors.primary
                        )
                    ) {

                    }
                    .padding(18.dp)
            )

            Text(
                text = "nikolay123@bk.ru",
                style = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 16.sp),
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, top = 40.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = stringResource(R.string.enter_code_title),
                style = FamilyOrganizerTheme.textStyle.headline2,
                lineHeight = 26.sp,
                modifier = Modifier
            )

            Text(
                text = stringResource(R.string.enter_code_subtitle),
                style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                    fontSize = 14.sp,
                    color = FamilyOrganizerTheme.colors.darkClay
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = code,
                onValueChange = { viewModel.onEvent(Event.CodeUpdate(it)) },
                textStyle = FamilyOrganizerTheme.textStyle.input,
                colors = outlinedTextFieldColors(),
                placeholder = { Text(text = stringResource(R.string.enter_code_hint)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Button(
                onClick = { viewModel.onEvent(Event.ContinueClicked) },
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
                onClick = { viewModel.onEvent(Event.ResendCodeClicked) },
                enabled = resendCodeEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {

                Text(
                    text = stringResource(R.string.send_code_again).uppercase(),
                    color = FamilyOrganizerTheme.colors.primary,
                    style = FamilyOrganizerTheme.textStyle.button,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}
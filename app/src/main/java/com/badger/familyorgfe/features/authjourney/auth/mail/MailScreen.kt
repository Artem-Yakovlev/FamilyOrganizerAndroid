package com.badger.familyorgfe.features.authjourney.auth.mail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun MailScreen() {

    var mail by remember {
        mutableStateOf("")
    }

    var continueEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            onValueChange = { mail = it },
            textStyle = FamilyOrganizerTheme.textStyle.input,
            colors = outlinedTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Button(
            onClick = { println("MY_TAG btn clicked") },
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
package com.badger.familyorgfe.features.authjourney.auth.code

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.style.outlinedTextFieldColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun CodeScreen() {

    var code by remember {
        mutableStateOf("")
    }

    var continueEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "",
                modifier = Modifier
                    .padding(18.dp)
                    .clickable {

                    }
            )
            Text(
                text = "nikolay123@bk.ru",
                style = FamilyOrganizerTheme.textStyle.primaryBody,
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
                style = FamilyOrganizerTheme.textStyle.boldHeading,
                lineHeight = 26.sp,
                modifier = Modifier
            )
            Text(
                text = stringResource(R.string.enter_code_subtitle),
                style = FamilyOrganizerTheme.textStyle.secondaryBody,
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                textStyle = FamilyOrganizerTheme.textStyle.primaryTitle,
                colors = outlinedTextFieldColors(),
                placeholder = { Text(text = stringResource(R.string.enter_code_hint)) },
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
                    text = stringResource(R.string.next_text).uppercase(),
                    color = FamilyOrganizerTheme.colors.whitePrimary,
                    style = FamilyOrganizerTheme.textStyle.boldBody,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            TextButton(
                onClick = { println("MY_TAG btn clicked") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = stringResource(R.string.send_code_again).uppercase(),
                    color = FamilyOrganizerTheme.colors.primary,
                    style = FamilyOrganizerTheme.textStyle.boldBody,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}
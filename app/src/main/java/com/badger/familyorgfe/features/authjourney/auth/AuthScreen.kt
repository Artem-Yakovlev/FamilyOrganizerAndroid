package com.badger.familyorgfe.features.authjourney.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun AuthScreen() {

    var mail by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Введите вашу почту",
            modifier = Modifier.padding(top = 64.dp),
            style = FamilyOrganizerTheme.textStyle.boldHeading
        )
        Text(
            text = "Если Вы еще не аккаунта, то мы его создадим",
            modifier = Modifier.padding(top = 8.dp),
            style = FamilyOrganizerTheme.textStyle.secondaryBody
        )
        TextField(
            value = mail,
            onValueChange = { mail = it; println("MY_TAG $it") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Button(
            onClick = { println("MY_TAG btn clicked") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Продолжить",
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}
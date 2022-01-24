package com.badger.familyorgfe.features.authjourney

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.badger.familyorgfe.features.authjourney.auth.code.CodeScreen
import com.badger.familyorgfe.features.authjourney.auth.entername.EnterNameScreen
import com.badger.familyorgfe.features.authjourney.auth.mail.MailScreen
import com.badger.familyorgfe.features.authjourney.auth.welcome.WelcomeScreen

@Composable
fun AuthJourney(modifier: Modifier) {

    CodeScreen(modifier)

}
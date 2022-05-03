package com.badger.familyorgfe.features.authjourney

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.badger.familyorgfe.features.authjourney.code.CodeScreen
import com.badger.familyorgfe.features.authjourney.mail.MailScreen
import com.badger.familyorgfe.utils.BackHandler

@Composable
fun AuthJourney(modifier: Modifier) {
    var step by remember { mutableStateOf<AuthJourneyStep>(AuthJourneyStep.Email) }

    when (step) {
        is AuthJourneyStep.Email -> {

            MailScreen(
                modifier = modifier,
                onEmailSent = { email ->
                    step = AuthJourneyStep.Code(email)
                }
            )
        }
        is AuthJourneyStep.Code -> {

            BackHandler {
                step = AuthJourneyStep.Email
            }

            CodeScreen(modifier = modifier)
        }
    }


}

sealed class AuthJourneyStep {
    object Email : AuthJourneyStep()
    data class Code(val email: String) : AuthJourneyStep()
}
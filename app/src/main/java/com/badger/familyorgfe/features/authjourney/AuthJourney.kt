package com.badger.familyorgfe.features.authjourney

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.badger.familyorgfe.features.authjourney.code.CodeScreen
import com.badger.familyorgfe.features.authjourney.entername.EnterNameScreen
import com.badger.familyorgfe.features.authjourney.mail.MailScreen
import com.badger.familyorgfe.utils.BackHandler

@Composable
fun AuthJourney(modifier: Modifier) {
    var step by remember { mutableStateOf<AuthJourneyStep>(AuthJourneyStep.Email) }

    val currentStep: AuthJourneyStep = step

    when (currentStep) {
        is AuthJourneyStep.Email -> {
            MailScreen(
                modifier = modifier,
                onEmailSent = { email ->
                    step = AuthJourneyStep.Code(email)
                }
            )
        }
        is AuthJourneyStep.Code -> {
            val onBack: () -> Unit = { step = AuthJourneyStep.Email }

            BackHandler(onBack = onBack)
            CodeScreen(
                modifier = modifier,
                emailArg = currentStep.email,
                onCodeVerified = {
                    step = AuthJourneyStep.Name(currentStep.email)
                },
                onBack = onBack
            )
        }
        is AuthJourneyStep.Name -> {
            EnterNameScreen(
                modifier = modifier
            )
        }
    }


}

sealed class AuthJourneyStep {
    object Email : AuthJourneyStep()
    data class Code(val email: String) : AuthJourneyStep()
    data class Name(val email: String) : AuthJourneyStep()
}
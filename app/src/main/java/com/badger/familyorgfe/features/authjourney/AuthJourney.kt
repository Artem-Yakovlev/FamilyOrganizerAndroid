package com.badger.familyorgfe.features.authjourney

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.badger.familyorgfe.ext.route
import com.badger.familyorgfe.features.authjourney.auth.code.CodeScreen
import com.badger.familyorgfe.features.authjourney.auth.entername.EnterNameScreen
import com.badger.familyorgfe.features.authjourney.auth.mail.MailScreen
import com.badger.familyorgfe.features.authjourney.auth.welcome.WelcomeScreen
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun AuthJourney(
    modifier: Modifier
) {
    Column(modifier = modifier.background(FamilyOrganizerTheme.colors.whitePrimary)) {

        val navController = rememberNavController()

        Content(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxWidth(),
            navController = navController
        )
    }
}

@Composable
private fun Content(modifier: Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AuthJourneyType.MAIL.route
    ) {
        composable(route = AuthJourneyType.MAIL.route) {
            MailScreen(modifier, navController)
        }
        composable(route = AuthJourneyType.CODE.route) {
            CodeScreen(modifier, navController)
        }
        composable(route = AuthJourneyType.ENTER_NAME.route) {
            EnterNameScreen(modifier, navController)
        }
        composable(route = AuthJourneyType.WELCOME.route) {
            WelcomeScreen(modifier, navController)
        }
    }
}

enum class AuthJourneyType {
    MAIL,
    CODE,
    ENTER_NAME,
    WELCOME
}
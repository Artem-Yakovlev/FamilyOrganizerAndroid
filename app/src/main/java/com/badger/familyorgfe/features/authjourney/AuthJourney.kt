package com.badger.familyorgfe.features.authjourney

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.badger.familyorgfe.ext.observe
import com.badger.familyorgfe.ext.route
import com.badger.familyorgfe.features.authjourney.auth.code.CodeScreen
import com.badger.familyorgfe.features.authjourney.auth.entername.EnterNameScreen
import com.badger.familyorgfe.features.authjourney.auth.mail.MailScreen
import com.badger.familyorgfe.features.authjourney.auth.welcome.WelcomeScreen
import com.badger.familyorgfe.navigation.NavigationCommand
import com.badger.familyorgfe.navigation.NavigationManager
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun AuthJourney(
    modifier: Modifier,
    lifecycleOwner: LifecycleOwner,
    navigationManager: NavigationManager
) {
    Column(modifier = modifier.background(FamilyOrganizerTheme.colors.whitePrimary)) {

        val navController = rememberNavController()

        Content(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxWidth(),
            navController = navController
        )
        lifecycleOwner.observe(navigationManager.commands) {
            handleCommand(it, navController)
        }
    }
}

private fun handleCommand(command: NavigationCommand, navController: NavHostController) {
    when (command) {
        is NavigationCommand.Back -> navController.navigateUp()
        is NavigationCommand.BackTo -> navController.popBackStack(command.route, command.inclusive)
        is NavigationCommand.To -> navController.navigate(command.route)
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
            MailScreen(modifier)
        }
        composable(route = AuthJourneyType.CODE.route) {
            CodeScreen(modifier)
        }
        composable(route = AuthJourneyType.ENTER_NAME.route) {
            EnterNameScreen(modifier)
        }
        composable(route = AuthJourneyType.WELCOME.route) {
            WelcomeScreen(modifier)
        }
    }
}

enum class AuthJourneyType {
    MAIL,
    CODE,
    ENTER_NAME,
    WELCOME
}
package com.badger.familyorgfe.features.appjourney

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.badger.familyorgfe.features.appjourney.adding.AddingScreen
import com.badger.familyorgfe.features.appjourney.fridge.FridgeScreen
import com.badger.familyorgfe.features.appjourney.profile.ProfileScreen
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun AppJourney(
    modifier: Modifier,
    viewModel: IAppJourneyViewModel = viewModel<AppJourneyViewModel>()
) {
    Column(modifier = modifier.background(FamilyOrganizerTheme.colors.whitePrimary)) {
        val navController = rememberNavController()

        Content(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxWidth(),
            navController = navController
        )

        BottomNavigation(
            modifier = Modifier.wrapContentSize(),
            navController = navController
        )
    }
}

@Composable
private fun Content(modifier: Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomNavigationType.FRIDGE.route
    ) {
        composable(route = BottomNavigationType.FRIDGE.route) {
            FridgeScreen(modifier)
        }
        composable(route = BottomNavigationType.ADDING.route) {
            AddingScreen(modifier)
        }
        composable(route = BottomNavigationType.PROFILE.route) {
            ProfileScreen(modifier)
        }
    }
}

private const val VISIBILITY_ANIMATION_MULTIPLIER = 0.7

@Composable
private fun BottomNavigation(
    modifier: Modifier,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(
        visible = currentDestination?.hierarchy
            ?.all { dest -> dest.route != BottomNavigationType.ADDING.route } == true,
        enter = slideInVertically { (VISIBILITY_ANIMATION_MULTIPLIER * it).toInt() } + fadeIn(),
        exit = slideOutVertically { (VISIBILITY_ANIMATION_MULTIPLIER * it).toInt() } + fadeOut(),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(18.dp),
                elevation = 3.dp
            ) {
                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    BottomNavigationItem(
                        type = BottomNavigationType.FRIDGE,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.width(62.dp))

                    BottomNavigationItem(
                        type = BottomNavigationType.ADDING,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.width(62.dp))

                    BottomNavigationItem(
                        type = BottomNavigationType.PROFILE,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationItem(
    type: BottomNavigationType,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { dest ->
        dest.route == type.route
    } == true

    Icon(
        modifier = Modifier
            .size(36.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    color = FamilyOrganizerTheme.colors.primary
                )
            ) {
                navController.navigate(type.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
        painter = painterResource(id = type.resourceId),
        contentDescription = null,
        tint = if (selected) {
            FamilyOrganizerTheme.colors.primary
        } else {
            FamilyOrganizerTheme.colors.darkClay
        }
    )
}
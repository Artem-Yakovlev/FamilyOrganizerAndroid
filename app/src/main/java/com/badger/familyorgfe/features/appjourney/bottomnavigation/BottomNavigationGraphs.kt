package com.badger.familyorgfe.features.appjourney.products

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.badger.familyorgfe.features.appjourney.BottomNavigationType
import com.badger.familyorgfe.features.appjourney.ProductsNavigationType
import com.badger.familyorgfe.features.appjourney.ProfileNavigationType
import com.badger.familyorgfe.features.appjourney.TasksNavigationType
import com.badger.familyorgfe.features.appjourney.products.adding.AddingScreen
import com.badger.familyorgfe.features.appjourney.products.fridge.FridgeScreen
import com.badger.familyorgfe.features.appjourney.products.scanner.ScannerScreen
import com.badger.familyorgfe.features.appjourney.profile.ProfileScreen
import com.badger.familyorgfe.features.appjourney.tasks.AllTasksScreen

fun NavGraphBuilder.productsNavGraph(
    modifier: Modifier,
    navController: NavController
) {
    navigation(
        startDestination = ProductsNavigationType.FRIDGE_SCREEN.route,
        route = BottomNavigationType.FRIDGE_ROUTE.route
    ) {
        composable(ProductsNavigationType.FRIDGE_SCREEN.route) {
            FridgeScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(ProductsNavigationType.MANUAL_ADDING_SCREEN.route) {
            AddingScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(ProductsNavigationType.AUTO_ADDING_SCREEN.route) {
            ScannerScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }
}

fun NavGraphBuilder.tasksNavGraph(
    modifier: Modifier,
    navController: NavController
) {
    navigation(
        startDestination = TasksNavigationType.ALL_TASKS_SCREEN.route,
        route = BottomNavigationType.ADDING_ROUTE.route
    ) {
        composable(TasksNavigationType.ALL_TASKS_SCREEN.route) {
            AllTasksScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }
}

fun NavGraphBuilder.profileNavGraph(
    modifier: Modifier,
    navController: NavController
) {
    navigation(
        startDestination = ProfileNavigationType.PROFILE_SCREEN.route,
        route = BottomNavigationType.PROFILE_ROUTE.route
    ) {
        composable(ProfileNavigationType.PROFILE_SCREEN.route) {
            ProfileScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }
}
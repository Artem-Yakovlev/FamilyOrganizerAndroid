package com.badger.familyorgfe.navigation

sealed class NavigationCommand {

    object Back : NavigationCommand()

    class To(val route: String) : NavigationCommand()

    class BackTo(val route: String, val inclusive: Boolean) : NavigationCommand()

}
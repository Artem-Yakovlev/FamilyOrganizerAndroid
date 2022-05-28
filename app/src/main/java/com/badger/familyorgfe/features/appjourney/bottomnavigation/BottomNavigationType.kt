package com.badger.familyorgfe.features.appjourney.bottomnavigation

import com.badger.familyorgfe.R

interface IRouteType {
    val route: String
}

enum class BottomNavigationType(
    val resourceId: Int
) : IRouteType {

    FRIDGE_ROUTE(
        resourceId = R.drawable.ic_bottom_navigation_fridge
    ),

    ADDING_ROUTE(
        resourceId = R.drawable.ic_bottom_navigation_tasks
    ),

    PROFILE_ROUTE(
        resourceId = R.drawable.ic_bottom_navigation_account
    );

    override val route: String = name
}

enum class ProductsNavigationType : IRouteType {

    FRIDGE_SCREEN,
    MANUAL_ADDING_SCREEN,
    AUTO_ADDING_SCREEN;

    override val route: String = name
}

enum class TasksNavigationType : IRouteType {

    ALL_TASKS_SCREEN,
    CREATE_TASK_SCREEN,
    TASK_DETAILS_SCREEN;

    override val route: String = name
}

enum class ProfileNavigationType : IRouteType {

    PROFILE_SCREEN;

    override val route: String = name
}

package com.badger.familyorgfe.features.appjourney.tasks.createtask

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun CreateTaskScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: ICreateTaskViewModel = hiltViewModel<CreateTaskViewModel>()
) {
}
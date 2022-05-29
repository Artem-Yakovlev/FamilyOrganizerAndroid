package com.badger.familyorgfe.features.appjourney.tasks.taskdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.TaskStatus
import com.badger.familyorgfe.features.appjourney.bottomnavigation.TasksNavigationType
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun TaskDetailsScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: ITaskDetailsViewModel = hiltViewModel<TaskDetailsViewModel>()
) {
    val nullableFamilyTask by viewModel.familyTask.collectAsState()

    nullableFamilyTask?.let { familyTask ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Toolbar(
                    status = familyTask.status,
                    onEditClicked = {
                        navController.navigate(TasksNavigationType.CREATE_TASK_SCREEN.route)
                    },
                    onBackClicked = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun Toolbar(
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    status: TaskStatus
) {
    BaseToolbar {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onBackClicked() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = stringResource(id = status.textResourceId),
            style = FamilyOrganizerTheme.textStyle.headline2,
            color = FamilyOrganizerTheme.colors.blackPrimary
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onEditClicked() },
            painter = painterResource(id = R.drawable.ic_edit_pencil),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}
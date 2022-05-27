package com.badger.familyorgfe.features.appjourney.tasks.alltasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badger.familyorgfe.R
import com.badger.familyorgfe.data.model.FamilyTask
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.utils.fabNestedScroll

@Composable
fun AllTasksScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: IAllTasksViewModel = hiltViewModel<AllTasksViewModel>()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Toolbar(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
            val fabHeightPx = with(LocalDensity.current) { 72.dp.roundToPx().toFloat() }
            val fabOffsetHeightPx = remember { mutableStateOf(0f) }
            val nestedScrollConnection = remember {
                fabNestedScroll(
                    fabHeightPx = fabHeightPx,
                    fabOffsetHeightPx = fabOffsetHeightPx
                )
            }

            val categories by viewModel.categories.collectAsState()
            val currentCategory by viewModel.currentCategory.collectAsState()

            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier.fillMaxWidth()) {
                categories.forEach { category ->
                    Spacer(modifier = Modifier.width(16.dp))
                    CategoryItem(
                        selected = category == currentCategory,
                        category = category,
                        onClick = {
                            viewModel.onEvent(
                                IAllTasksViewModel.Event.OnCategorySelected(category)
                            )
                        })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {

            }

        }
    }
}

@Composable
private fun Toolbar(
    onBackClicked: () -> Unit
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
            text = stringResource(id = R.string.adding_toolbar_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            color = FamilyOrganizerTheme.colors.blackPrimary
        )

        Spacer(modifier = Modifier.width(40.dp))
    }
}

@Composable
private fun CategoryItem(
    selected: Boolean,
    category: IAllTasksViewModel.Category,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(48.dp)
            .background(FamilyOrganizerTheme.colors.whitePrimary),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp),
        elevation = 3.dp
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = rememberRipple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = category.resourceId),
                contentDescription = null,
                tint = if (selected) {
                    FamilyOrganizerTheme.colors.primary
                } else {
                    FamilyOrganizerTheme.colors.blackPrimary
                }
            )
        }
    }
}

@Composable
private fun TaskGridItem(
    familyTask: FamilyTask
) {
    Card {

    }
}
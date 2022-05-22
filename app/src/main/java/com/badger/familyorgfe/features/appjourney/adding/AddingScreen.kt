package com.badger.familyorgfe.features.appjourney.adding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.badger.familyorgfe.R
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeListItem
import com.badger.familyorgfe.ui.elements.BaseDialog
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.utils.BackHandler

@Composable
fun AddingScreen(
    modifier: Modifier,
    navOnBack: () -> Unit,
    viewModel: IAddingViewModel = hiltViewModel<AddingViewModel>()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        val onBack: () -> Unit = {
            val event = IAddingViewModel.Event.OnBackClicked
            viewModel.onEvent(event)
            navOnBack()
        }
        BackHandler(onBack = onBack)

        Toolbar(
            onBackClicked = onBack,
            onDoneClicked = {}
        )

        val fridgeItems by viewModel.items.collectAsState()
        val expandedItemId by viewModel.expandedItemId.collectAsState()
        val deleteDialog by viewModel.deleteItemDialog.collectAsState()

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(
                items = fridgeItems,
                key = { item -> item.id }
            ) { item ->
                FridgeListItem(
                    item = item,
                    isExpanded = item.id == expandedItemId,
                    onExpand = {
                        val event = IAddingViewModel.Event.OnItemExpanded(item.id)
                        viewModel.onEvent(event)
                    },
                    onCollapse = {
                        val event = IAddingViewModel.Event.OnItemCollapsed
                        viewModel.onEvent(event)
                    },
                    onEdit = {

                    },
                    onDelete = { deletedItem ->
                        val event = IAddingViewModel.Event.RequestDeleteItemDialog(deletedItem)
                        viewModel.onEvent(event)
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        }

        deleteDialog?.let { fridgeItem ->

            BaseDialog(
                titleText = stringResource(id = R.string.fridge_toolbar_title),
                descriptionText = stringResource(
                    id = R.string.fridge_delete_product_description,
                    fridgeItem.name
                ),
                dismissText = stringResource(id = R.string.fridge_delete_product_dismiss),
                actionText = stringResource(id = R.string.fridge_delete_product_action),
                onDismissClicked = {
                    val event = IAddingViewModel.Event.DismissDeleteDialog
                    viewModel.onEvent(event)
                },
                onActionClicked = {
                    val event = IAddingViewModel.Event.DeleteItem(fridgeItem)
                    viewModel.onEvent(event)
                })

        }
    }
}

@Composable
private fun Toolbar(
    onBackClicked: () -> Unit,
    onDoneClicked: () -> Unit
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

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onDoneClicked },
            painter = painterResource(id = R.drawable.ic_done),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )


        Spacer(modifier = Modifier.width(16.dp))
    }
}
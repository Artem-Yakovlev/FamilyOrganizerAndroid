package com.badger.familyorgfe.features.appjourney.fridge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.features.appjourney.fridge.fridgeitem.FridgeListItem
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun FridgeScreen(
    modifier: Modifier,
    viewModel: IFridgeViewModel = hiltViewModel<FridgeViewModel>()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Toolbar()

        val fridgeItems by viewModel.items.collectAsState()
        val expandedItemId by viewModel.expandedItemId.collectAsState()

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            fridgeItems.forEach { item ->
                item {
                    FridgeListItem(
                        item = item,
                        isExpanded = item.id == expandedItemId,
                        onExpand = {
                            val event = IFridgeViewModel.Event.OnItemExpanded(item.id)
                            viewModel.onEvent(event)
                        },
                        onCollapse = {
                            val event = IFridgeViewModel.Event.OnItemCollapsed
                            viewModel.onEvent(event)
                        },
                        onEdit = {},
                        onDelete = {}
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Toolbar() {
    BaseToolbar {
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.fridge_toolbar_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            color = FamilyOrganizerTheme.colors.blackPrimary
        )

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { },
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { },
            painter = painterResource(id = R.drawable.ic_more_vert),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}

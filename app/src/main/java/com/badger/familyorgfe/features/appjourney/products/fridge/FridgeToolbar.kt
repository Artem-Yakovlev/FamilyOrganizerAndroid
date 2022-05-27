package com.badger.familyorgfe.features.appjourney.products.fridge

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun FridgeToolbar(
    isSearchActive: Boolean,
    startSearch: () -> Unit,
    closeSearch: () -> Unit,
    currentSearchQuery: String,
    searchQueryChanged: (String) -> Unit,
    clearSearchQuery: () -> Unit
) {

    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(color = FamilyOrganizerTheme.colors.lightClay)
    ) {
        AnimatedContent(targetState = isSearchActive) { isSearchBarVisible ->

            Row(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.Bottom,
            ) {

                if (isSearchBarVisible) {
                    SearchToolbar(
                        currentSearchQuery = currentSearchQuery,
                        closeSearch = closeSearch,
                        searchQueryChanged = searchQueryChanged,
                        clearSearchQuery = clearSearchQuery
                    )
                } else {
                    StaticToolbar(
                        startSearch = startSearch
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.SearchToolbar(
    currentSearchQuery: String,
    closeSearch: () -> Unit,
    searchQueryChanged: (String) -> Unit,
    clearSearchQuery: () -> Unit
) {
    val focusRequester = FocusRequester()

    ToolbarIcon(
        resourceId = R.drawable.ic_back,
        onClick = closeSearch
    )
    Spacer(modifier = Modifier.width(8.dp))

    BasicTextField(
        modifier = Modifier
            .weight(1f)
            .focusRequester(focusRequester),
        value = currentSearchQuery,
        onValueChange = searchQueryChanged,
        singleLine = true,
        textStyle = FamilyOrganizerTheme.textStyle.body.copy(fontSize = 18.sp)
    )
    Spacer(modifier = Modifier.width(8.dp))

    AnimatedVisibility(
        visible = currentSearchQuery.isNotEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ToolbarIcon(
            resourceId = R.drawable.ic_close,
            onClick = clearSearchQuery
        )
    }

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Composable
private fun RowScope.StaticToolbar(
    startSearch: () -> Unit
) {
    Text(
        modifier = Modifier.weight(1f),
        text = stringResource(id = R.string.fridge_toolbar_title),
        style = FamilyOrganizerTheme.textStyle.headline2,
        color = FamilyOrganizerTheme.colors.blackPrimary
    )

    ToolbarIcon(
        resourceId = R.drawable.ic_search,
        onClick = startSearch
    )
}

@Composable
private fun ToolbarIcon(
    resourceId: Int,
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier
            .size(24.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    color = FamilyOrganizerTheme.colors.darkClay
                )
            ) { onClick() },
        painter = painterResource(id = resourceId),
        contentDescription = null,
        tint = FamilyOrganizerTheme.colors.blackPrimary
    )

}
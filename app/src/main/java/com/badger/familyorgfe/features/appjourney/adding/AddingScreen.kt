package com.badger.familyorgfe.features.appjourney.adding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.elements.BaseToolbar
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun AddingScreen(
    modifier: Modifier,
    viewModel: IAddingViewModel = viewModel<AddingViewModel>()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Toolbar()

        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {

        }
    }
}

@Composable
private fun Toolbar() {
    BaseToolbar {

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { },
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
                ) { },
            painter = painterResource(id = R.drawable.ic_done),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.blackPrimary
        )


        Spacer(modifier = Modifier.width(16.dp))
    }
}
package com.badger.familyorgfe.features.appjourney.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun ProfileScreen(modifier: Modifier) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .background(color = FamilyOrganizerTheme.colors.lightClay),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.profile_toolbar_title),
                style = FamilyOrganizerTheme.textStyle.headline2,
                color = FamilyOrganizerTheme.colors.blackPrimary
            )

        }
    }
}
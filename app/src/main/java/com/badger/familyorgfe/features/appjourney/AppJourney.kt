package com.badger.familyorgfe.features.appjourney

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme

@Composable
fun AppJourney(modifier: Modifier) {
    Column(modifier = modifier.background(Color.White)) {
        Content(modifier = Modifier.weight(weight = 1f))

        BottomNavigation(modifier = Modifier.wrapContentSize())
    }
}

@Composable
private fun Content(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Blue)
    )
}

@Composable
private fun BottomNavigation(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(18.dp),
            elevation = 3.dp
        ) {

            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavigationItem(type = BottomNavigationType.FRIDGE, selected = true)
                Spacer(modifier = Modifier.width(62.dp))

                BottomNavigationItem(type = BottomNavigationType.ADDING, selected = false)
                Spacer(modifier = Modifier.width(62.dp))

                BottomNavigationItem(type = BottomNavigationType.PROFILE, selected = false)
            }
        }
    }
}

enum class BottomNavigationType(val resourceId: Int) {
    FRIDGE(resourceId = R.drawable.ic_bottom_navigation_fridge),
    ADDING(resourceId = R.drawable.ic_bottom_navigation_plus),
    PROFILE(resourceId = R.drawable.ic_bottom_navigation_account)
}

@Composable
private fun BottomNavigationItem(type: BottomNavigationType, selected: Boolean) {
    Icon(
        modifier = Modifier.size(36.dp),
        painter = painterResource(id = type.resourceId),
        contentDescription = null,
        tint = if (selected) {
            FamilyOrganizerTheme.colors.primary
        } else {
            FamilyOrganizerTheme.colors.darkClay
        }
    )
}
package com.badger.familyorgfe.utils

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import kotlin.math.roundToInt

fun fabNestedScroll(
    fabHeightPx: Float,
    fabOffsetHeightPx: MutableState<Float>
) =
    object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

            val delta = available.y
            val newOffset = fabOffsetHeightPx.value + delta
            fabOffsetHeightPx.value = newOffset.coerceIn(-fabHeightPx, 0f)

            return Offset.Zero
        }
    }

@Composable
fun BoxScope.Fab(
    fabOffsetHeightPx: Float,
    resourceId: Int,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
            .offset { IntOffset(x = 0, y = -fabOffsetHeightPx.roundToInt()) },
        backgroundColor = FamilyOrganizerTheme.colors.primary,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.Center),
            painter = painterResource(
                id = resourceId
            ),
            contentDescription = null,
            tint = FamilyOrganizerTheme.colors.whitePrimary
        )
    }
}
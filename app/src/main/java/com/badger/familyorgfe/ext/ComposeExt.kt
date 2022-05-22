package com.badger.familyorgfe.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableWithoutIndication(onClick: () -> Unit) : Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
    ) { onClick() }
}
package com.badger.familyorgfe.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

class NavigationManager {

    val commands = MutableSharedFlow<NavigationCommand>(
        extraBufferCapacity = 5,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun navigate(
        direction: NavigationCommand
    ) {
        commands.tryEmit(direction)
    }

}
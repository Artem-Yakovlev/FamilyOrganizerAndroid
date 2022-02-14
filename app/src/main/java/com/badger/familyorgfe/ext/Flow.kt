package com.badger.familyorgfe.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <reified T> Flow<T>.observe(
    scope: CoroutineScope,
    noinline action: suspend (T) -> Unit
) = onEach { action(it) }.launchIn(scope)
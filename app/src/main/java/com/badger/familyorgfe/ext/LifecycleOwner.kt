package com.badger.familyorgfe.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

inline fun <reified T> LifecycleOwner.observe(
    sharedFlow: SharedFlow<T>,
    crossinline action: suspend (T) -> Unit
) = sharedFlow.observe(lifecycleScope) { action(it) }

inline fun <reified T> CoroutineScope.observe(
    sharedFlow: SharedFlow<T>,
    crossinline action: suspend (T) -> Unit
) = sharedFlow.observe(this) { action(it) }

inline fun <reified T> CoroutineScope.observeWithContext(
    sharedFlow: SharedFlow<T>,
    context: CoroutineContext,
    crossinline action: suspend (T) -> Unit
) = sharedFlow.observe(this) { withContext(context) { action(it) } }
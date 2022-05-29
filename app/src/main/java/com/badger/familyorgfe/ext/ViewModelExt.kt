package com.badger.familyorgfe.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

fun ViewModel.longRunning(block: suspend () -> Any?) {

    viewModelScope.launch(Dispatchers.Default) {
        block()
    }
}

fun ViewModel.viewModelScope(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    onError: (Throwable) -> Unit = { throwable -> throwable.printStackTrace() }
) = viewModelScope + dispatcher + CoroutineExceptionHandler { _, throwable -> onError(throwable) }

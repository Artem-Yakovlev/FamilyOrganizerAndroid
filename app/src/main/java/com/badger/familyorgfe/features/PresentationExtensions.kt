package com.badger.familyorgfe.features

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun String.isValidMail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidName() = isNotBlank()

fun ViewModel.longRunning(block: suspend () -> Any?) {
    viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}
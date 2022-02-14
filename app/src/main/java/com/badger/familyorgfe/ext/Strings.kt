package com.badger.familyorgfe.ext

import android.util.Patterns

fun String.isValidMail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidCode() = isNotBlank() && length >= 6

fun String.isValidName() = isNotBlank() && length >= 2

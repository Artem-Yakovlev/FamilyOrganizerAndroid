package com.badger.familyorgfe.ext

import android.util.Patterns

fun String.isValidMail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidName() = isNotEmpty() && length >= 2

package com.badger.familyorgfe.ext

import android.util.Patterns

fun String.isValidMail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidUserName() = isNotEmpty() && length >= 2

fun String.isValidProductName() = trim().length >= 2

package com.badger.familyorgfe.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun hideKeyboard() = true.apply { LocalSoftwareKeyboardController.current?.hide() }

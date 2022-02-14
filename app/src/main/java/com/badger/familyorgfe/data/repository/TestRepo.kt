package com.badger.familyorgfe.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

object TestRepo {
    val isUserAuthorized = MutableStateFlow(false)
}
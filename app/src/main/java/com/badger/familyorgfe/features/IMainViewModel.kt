package com.badger.familyorgfe.features

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface IMainViewModel : IBaseViewModel<Nothing> {

    val isUserAuthorized: StateFlow<Boolean>
}
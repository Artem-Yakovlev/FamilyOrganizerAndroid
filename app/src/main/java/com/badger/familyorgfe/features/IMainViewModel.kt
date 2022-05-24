package com.badger.familyorgfe.features

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface IMainViewModel : IBaseViewModel<IMainViewModel.Event> {

    val isAuth: StateFlow<Boolean?>

    val hasFamily: StateFlow<Boolean?>

    sealed class Event {
        object Init : Event()
    }
}
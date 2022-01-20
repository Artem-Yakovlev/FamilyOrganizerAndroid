package com.badger.familyorgfe.features

import com.badger.familyorgfe.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel(), IMainViewModel {

    override val isAuth = MutableStateFlow(value = true)

    override fun clearData() = Unit
}
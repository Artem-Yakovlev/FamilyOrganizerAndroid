package com.badger.familyorgfe.features

import androidx.lifecycle.viewModelScope
import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.commoninteractors.IsAuthedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    isAuthedUseCase: IsAuthedUseCase
) : BaseViewModel(), IMainViewModel {

    override val isAuth = isAuthedUseCase(Unit).stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    override fun clearData() = Unit
}
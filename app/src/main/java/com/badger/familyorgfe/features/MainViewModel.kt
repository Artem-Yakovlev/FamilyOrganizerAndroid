package com.badger.familyorgfe.features

import androidx.lifecycle.viewModelScope
import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.commoninteractors.SubscribeIsUserAuthorizedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.badger.familyorgfe.features.IMainViewModel.AuthState
import kotlinx.coroutines.flow.map

@HiltViewModel
class MainViewModel @Inject constructor(
    subscribeIsUserAuthorizedUseCase: SubscribeIsUserAuthorizedUseCase
) : BaseViewModel(), IMainViewModel {

    override val isAuthorized = subscribeIsUserAuthorizedUseCase.invoke(Unit)
        .map { if (it) AuthState.Auth else AuthState.NoAuth }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = AuthState.Loading
        )

    override fun clearData() = Unit
}
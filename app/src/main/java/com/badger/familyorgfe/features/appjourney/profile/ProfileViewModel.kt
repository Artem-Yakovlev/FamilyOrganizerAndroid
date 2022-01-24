package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.commoninteractors.GetMainUserUseCase
import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.ext.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getMainUserUseCase: GetMainUserUseCase
) : BaseViewModel(), IProfileViewModel {

    override val mainUser: StateFlow<User> = getMainUserUseCase(Unit)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope(Dispatchers.Default),
            started = SharingStarted.Lazily,
            initialValue = User.createEmpty()
        )
}
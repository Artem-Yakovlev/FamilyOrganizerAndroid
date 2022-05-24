package com.badger.familyorgfe.features

import androidx.lifecycle.viewModelScope
import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.commoninteractors.HasFamilyUseCase
import com.badger.familyorgfe.commoninteractors.IsAuthedUseCase
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.fcm.domain.SendFcmTokenUseCase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sendFcmTokenUseCase: SendFcmTokenUseCase,
    isAuthedUseCase: IsAuthedUseCase,
    hasFamilyUseCase: HasFamilyUseCase
) : BaseViewModel(), IMainViewModel {

    override val isAuth = isAuthedUseCase(Unit).stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    override val hasFamily: StateFlow<Boolean?> = hasFamilyUseCase(Unit).stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    override fun onEvent(event: IMainViewModel.Event) {
        when (event) {
            IMainViewModel.Event.Init -> updateFcmToken()
        }
    }

    private fun updateFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            val token = task.result
            longRunning {
                sendFcmTokenUseCase(token)
            }
        }
    }

    override fun clearData() = Unit
}
package com.badger.familyorgfe.features.authjourney.entername

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.repository.IDataStoreRepository
import com.badger.familyorgfe.ext.isValidName
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.authjourney.entername.IEnterNameViewModel.Event
import com.badger.familyorgfe.features.authjourney.entername.domain.GetProfileUseCase
import com.badger.familyorgfe.features.authjourney.entername.domain.UpdateProfileNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EnterNameViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileNameUseCase: UpdateProfileNameUseCase,
    private val dataStoreRepository: IDataStoreRepository
) : BaseViewModel(), IEnterNameViewModel {

    private var userId: String? = null

    override val name = MutableStateFlow("")

    override val isLoading = MutableStateFlow(false)

    override val continueEnabled = MutableStateFlow(false)

    override fun onEvent(event: Event) {
        when (event) {
            is Event.Init -> longRunning { init() }
            is Event.NameUpdate -> handleNameUpdate(event.query)
            is Event.ContinueClick -> longRunning { handleContinueClick(event.name) }
            is Event.SkipClick -> longRunning { handleSkipClick() }
        }
    }

    private suspend fun init() {
        getProfileUseCase(Unit)?.let { user ->
            if (user.name.isValidName()) {
                dataStoreRepository.setUserId(user.id)
            } else {
                userId = user.id
            }
        }
    }

    private fun handleNameUpdate(query: String) {
        name.value = query
        continueEnabled.value = query.isValidName()
    }

    private suspend fun handleContinueClick(name: String) {
        continueEnabled.value = false
        isLoading.value = true
        updateProfileNameUseCase(name)
        userId?.let { id -> dataStoreRepository.setUserId(id) }
    }

    private suspend fun handleSkipClick() {
        isLoading.value = true
        userId?.let { id -> dataStoreRepository.setUserId(id) }
    }

    override fun clearData() = Unit
}
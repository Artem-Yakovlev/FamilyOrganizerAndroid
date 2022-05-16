package com.badger.familyorgfe.features.familyauthjourney.all

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.familyauthjourney.all.domain.*
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFamiliesViewModel @Inject constructor(
    private val getFamiliesUseCase: GetFamiliesUseCase,
    private val acceptInviteUseCase: AcceptInviteUseCase,
    private val declineInviteUseCase: DeclineInviteUseCase,
    private val setFamilyIdUseCase: SetFamilyIdUseCase,
    private val createFamilyUseCase: CreateFamilyUseCase
) : BaseViewModel(), IAllFamiliesViewModel {

    override val familiesAndInvites = MutableStateFlow(FamiliesAndInvites.createEmpty())

    override val isCreating = MutableStateFlow(false)

    override val name = MutableStateFlow("")

    override val createEnabled = MutableStateFlow(false)

    override fun onEvent(event: IAllFamiliesViewModel.Event) {
        when (event) {
            is IAllFamiliesViewModel.Event.Init -> doActionAndUpdateFamilies {
                getFamiliesUseCase(Unit)
            }
            is IAllFamiliesViewModel.Event.AcceptInvite -> doActionAndUpdateFamilies {
                acceptInviteUseCase(event.id)
            }
            is IAllFamiliesViewModel.Event.DeclineInvite -> doActionAndUpdateFamilies {
                declineInviteUseCase(event.id)
            }
            is IAllFamiliesViewModel.Event.CreateFamily -> longRunning {
                if (createEnabled.value) {
                    createFamilyUseCase(event.name)
                }
                apply { createEnabled.value = false }
            }
            is IAllFamiliesViewModel.Event.OpenFamily -> longRunning {
                setFamilyIdUseCase(event.id)
            }
            is IAllFamiliesViewModel.Event.OnDismissDialog -> {
                isCreating.value = false
                name.value = ""
            }
            is IAllFamiliesViewModel.Event.OnNameChanged -> {
                name.value = event.name
                createEnabled.value = event.name.length >= MIN_NAME_LENGTH
            }
            is IAllFamiliesViewModel.Event.StartFamilyCreating -> {
                isCreating.value = true
            }
        }
    }

    private fun doActionAndUpdateFamilies(action: suspend () -> FamiliesAndInvites) =
        viewModelScope().launch {
            familiesAndInvites.value = action()
        }

    companion object {
        private const val MIN_NAME_LENGTH = 3L
    }
}
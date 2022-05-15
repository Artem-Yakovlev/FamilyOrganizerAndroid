package com.badger.familyorgfe.features.familyauthjourney.all

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.familyauthjourney.all.domain.AcceptInviteUseCase
import com.badger.familyorgfe.features.familyauthjourney.all.domain.DeclineInviteUseCase
import com.badger.familyorgfe.features.familyauthjourney.all.domain.GetFamiliesUseCase
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFamiliesViewModel @Inject constructor(
    private val getFamiliesUseCase: GetFamiliesUseCase,
    private val acceptInviteUseCase: AcceptInviteUseCase,
    private val declineInviteUseCase: DeclineInviteUseCase
) : BaseViewModel(), IAllFamiliesViewModel {

    override val familiesAndInvites = MutableStateFlow(FamiliesAndInvites.createEmpty())
//
    override val isCreating = MutableStateFlow(false)

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
            is IAllFamiliesViewModel.Event.CreateFamily -> {
                isCreating.value = true
            }
            is IAllFamiliesViewModel.Event.OpenFamily -> TODO()
            is IAllFamiliesViewModel.Event.OpenFamilySettings -> TODO()
        }
    }

    private fun doActionAndUpdateFamilies(action: suspend () -> FamiliesAndInvites) =
        viewModelScope().launch {
            familiesAndInvites.value = action()
        }
}
package com.badger.familyorgfe.features.familyauthjourney.all

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import kotlinx.coroutines.flow.StateFlow

interface IAllFamiliesViewModel : IBaseViewModel<IAllFamiliesViewModel.Event> {

    val familiesAndInvites: StateFlow<FamiliesAndInvites>

    val isCreating: StateFlow<Boolean>
    val name: StateFlow<String>
    val createEnabled: StateFlow<Boolean>

    sealed class Event {
        object Init : Event()
        data class OpenFamily(val id: Long) : Event()
        data class AcceptInvite(val id: Long) : Event()
        data class DeclineInvite(val id: Long) : Event()
        object StartFamilyCreating : Event()
        object OnDismissDialog : Event()
        data class OnNameChanged(val name: String) : Event()
        data class CreateFamily(val name: String) : Event()
    }
}
package com.badger.familyorgfe.features.familyauthjourney.all

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.features.familyauthjourney.all.model.FamiliesAndInvites
import kotlinx.coroutines.flow.StateFlow

interface IAllFamiliesViewModel : IBaseViewModel<IAllFamiliesViewModel.Event> {

    val familiesAndInvites: StateFlow<FamiliesAndInvites>
//
    val isCreating: StateFlow<Boolean>

    sealed class Event {
        object Init : Event()
        data class OpenFamily(val id: Long) : Event()
        data class OpenFamilySettings(val id: Long) : Event()
        data class AcceptInvite(val id: Long) : Event()
        data class DeclineInvite(val id: Long) : Event()
        object CreateFamily : Event()
    }
}
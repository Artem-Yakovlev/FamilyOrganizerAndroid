package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.UserStatus
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import kotlinx.coroutines.flow.StateFlow
import java.io.File

interface IProfileViewModel : IBaseViewModel<IProfileViewModel.Event> {

    val showLogoutDialog: StateFlow<Boolean>

    val mainUser: StateFlow<FamilyMember>

    val members: StateFlow<List<FamilyMember>>

    val editFamilyMemberDialog: StateFlow<FamilyMember?>

    val editFamilyMemberText: StateFlow<String>

    val editFamilyMemberSaveEnabled: StateFlow<Boolean>

    val excludeFamilyMemberDialog: StateFlow<FamilyMember?>

    val changeStatusDialog: StateFlow<Boolean>

    sealed class Event {
        object OnLogoutClick : Event()
        object OnLogoutDismiss : Event()
        object OnLogoutAccepted : Event()

        data class OnEditMemberClicked(val familyMember: FamilyMember) : Event()
        data class OnEditMemberTextChanged(val text: String) : Event()
        data class OnMemberLocalNameSaved(val email: String, val localName: String) : Event()
        object OnEditMemberDismiss : Event()

        data class OnExcludeFamilyMemberClick(val familyMember: FamilyMember) : Event()
        object OnExcludeDismiss : Event()
        data class OnExcludeFamilyMemberAccepted(val familyMember: FamilyMember) : Event()

        data class ShowStatusMenu(val show: Boolean) : Event()
        data class ChangeStatus(val status: UserStatus) : Event()

        data class OnProfileImageChanged(val file: File) : Event()
    }
}
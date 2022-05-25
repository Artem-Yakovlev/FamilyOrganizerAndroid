package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.R
import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.UserStatus
import com.badger.familyorgfe.ext.isValidMail
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

    val addUserDialogState: StateFlow<Event.AddUserDialogState?>

    sealed class Event {
        sealed class Ordinal : Event() {
            object OnLogoutClick : Ordinal()
            object OnLogoutDismiss : Ordinal()
            object OnLogoutAccepted : Ordinal()

            data class OnEditMemberClicked(val familyMember: FamilyMember) : Ordinal()
            data class OnEditMemberTextChanged(val text: String) : Ordinal()
            data class OnMemberLocalNameSaved(val email: String, val localName: String) : Ordinal()
            object OnEditMemberDismiss : Ordinal()

            data class OnExcludeFamilyMemberClick(val familyMember: FamilyMember) : Ordinal()
            object OnExcludeDismiss : Ordinal()
            data class OnExcludeFamilyMemberAccepted(val familyMember: FamilyMember) : Ordinal()

            data class ShowStatusMenu(val show: Boolean) : Ordinal()
            data class ChangeStatus(val status: UserStatus) : Ordinal()

            data class OnProfileImageChanged(val file: File) : Ordinal()
            object OnInviteFamilyMemberClicked : Ordinal()
        }

        sealed class AddUserDialog : Event() {
            data class OnInputTextChanged(
                val text: String
            ) : AddUserDialog()

            data class SendInvite(
                val email: String
            ) : AddUserDialog()

            object Close : AddUserDialog()
        }

        data class AddUserDialogState(
            val textInput: String,
            val loading: Boolean,
            val error: ErrorType? = null
        ) {
            val hasNoErrors = error == null

            val actionEnabled = textInput.isValidMail() && error == null

            companion object {
                fun createEmpty() = AddUserDialogState(
                    textInput = "",
                    loading = false,
                    error = null
                )
            }
        }
    }

    enum class ErrorType(val messageResId: Int) {
        USER_DOES_NOT_EXIST(R.string.invite_member_dialog_not_found_error),
        USER_ALREADY_IN_FAMILY(R.string.invite_member_dialog_already_in_family),
        USER_ALREADY_INVITED(R.string.invite_member_dialog_already_invited),
        UNEXPECTED(R.string.invite_member_dialog_unexpected_error)
    }
}
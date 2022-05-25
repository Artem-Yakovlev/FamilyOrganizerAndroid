package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.commoninteractors.GetMainUserUseCase
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.ext.isValidUserName
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.profile.domain.*
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getMainUserUseCase: GetMainUserUseCase,
    private val getAllFamilyMembersUseCase: GetAllFamilyMembersUseCase,
    private val saveLocalNameUseCase: SaveLocalNameUseCase,
    private val excludeFamilyMemberUseCase: ExcludeFamilyMemberUseCase,
    private val updateStatusUseCase: UpdateStatusUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel(), IProfileViewModel {

    private val refreshAllMembersCrutch: MutableStateFlow<Long> = MutableStateFlow(0L)

    override val mainUser: StateFlow<FamilyMember> = refreshAllMembersCrutch
        .flatMapLatest { flow { emit(getMainUserUseCase(Unit)) } }
        .map(FamilyMember::createForMainUser)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope(),
            started = SharingStarted.Lazily,
            initialValue = FamilyMember.createEmpty()
        )

    override val editFamilyMemberDialog: MutableStateFlow<FamilyMember?> =
        MutableStateFlow(null)
    override val editFamilyMemberText: MutableStateFlow<String> =
        MutableStateFlow("")
    override val editFamilyMemberSaveEnabled: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    override val excludeFamilyMemberDialog: MutableStateFlow<FamilyMember?> =
        MutableStateFlow(null)
    override val changeStatusDialog: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    override val members: StateFlow<List<FamilyMember>> = refreshAllMembersCrutch
        .flatMapLatest { getAllFamilyMembersUseCase(Unit) }
        .stateIn(
            scope = viewModelScope(),
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    override val showLogoutDialog = MutableStateFlow(false)

    override fun onEvent(event: IProfileViewModel.Event) {
        when (event) {
            is IProfileViewModel.Event.OnLogoutClick -> {
                showLogoutDialog.value = true
            }
            is IProfileViewModel.Event.OnLogoutAccepted -> longRunning {
                logoutUseCase(Unit)
                showLogoutDialog.value = false
                Unit
            }
            is IProfileViewModel.Event.OnLogoutDismiss -> {
                showLogoutDialog.value = false
            }
            is IProfileViewModel.Event.OnEditMemberClicked -> {
                editFamilyMemberText.value = event.familyMember.name
                editFamilyMemberSaveEnabled.value = event.familyMember.name.isValidUserName()
                editFamilyMemberDialog.value = event.familyMember
            }
            is IProfileViewModel.Event.OnEditMemberDismiss -> longRunning {
                closeEditMemberDialog()
            }
            is IProfileViewModel.Event.OnEditMemberTextChanged -> longRunning {
                editFamilyMemberText.value = event.text
                editFamilyMemberSaveEnabled.value = event.text.isValidUserName()
                Unit
            }
            is IProfileViewModel.Event.OnMemberLocalNameSaved -> longRunning {
                saveLocalNameUseCase(LocalName(event.email, event.localName))
                closeEditMemberDialog()
            }
            is IProfileViewModel.Event.OnExcludeDismiss -> {
                excludeFamilyMemberDialog.value = null
            }
            is IProfileViewModel.Event.OnExcludeFamilyMemberAccepted -> longRunning {
                excludeFamilyMemberUseCase(event.familyMember)
                updateCrutch()
                closeEditMemberDialog()
                excludeFamilyMemberDialog.value = null
                Unit
            }
            is IProfileViewModel.Event.OnExcludeFamilyMemberClick -> {
                excludeFamilyMemberDialog.value = event.familyMember
            }
            is IProfileViewModel.Event.ShowStatusMenu -> {
                changeStatusDialog.value = event.show
            }
            is IProfileViewModel.Event.ChangeStatus -> longRunning {
                updateStatusUseCase(event.status)
                updateCrutch()
                changeStatusDialog.value = false
                Unit
            }
            is IProfileViewModel.Event.OnProfileImageChanged -> longRunning {
                updateProfileImageUseCase(event.file)
                updateCrutch()
            }
        }
    }

    private fun updateCrutch() {
        refreshAllMembersCrutch.value = System.currentTimeMillis()
    }

    private fun closeEditMemberDialog() {
        editFamilyMemberDialog.value = null
        editFamilyMemberText.value = ""
        editFamilyMemberSaveEnabled.value = false
    }

}
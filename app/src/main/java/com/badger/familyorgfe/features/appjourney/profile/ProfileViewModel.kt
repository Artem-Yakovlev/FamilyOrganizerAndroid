package com.badger.familyorgfe.features.appjourney.profile

import android.util.Log
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
    getAllOnlineUsersForFamily: GetAllOnlineUsersForFamily,
    private val getAllLocalNamesUseCase: GetAllLocalNamesUseCase,
    private val saveLocalNameUseCase: SaveLocalNameUseCase,
    private val excludeFamilyMemberUseCase: ExcludeFamilyMemberUseCase,
    private val updateStatusUseCase: UpdateStatusUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
    private val inviteFamilyMemberUseCase: InviteFamilyMemberUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel(), IProfileViewModel {

    private val refreshAllMembersCrutch: MutableStateFlow<Long> = MutableStateFlow(0L)

    override val mainUser: StateFlow<FamilyMember> =
        refreshAllMembersCrutch
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
    override val addUserDialogState: MutableStateFlow<IProfileViewModel.Event.AddUserDialogState?> =
        MutableStateFlow(null)

//    private val onlineUsers = refreshAllMembersCrutch
//        .flatMapLatest { flow { emit(getAllOnlineUsersForFamily(Unit)) } }

    private val onlineUsers = flow { emit(getAllOnlineUsersForFamily(Unit)) }

    private val localNames = getAllLocalNamesUseCase(Unit)

    override val members: StateFlow<List<FamilyMember>> = onlineUsers.map {
        Log.d("ASMR1", it.toString())

        val result = it.map { user ->
//            resultList.add(
                FamilyMember.createForOnlineUser(name = user.name, onlineUser = user)
//                createForOnlineUser(name = user.name)
//                FamilyMember.createEmpty()
//            )
        }

        Log.d("ASMR2", result.toString())
//        emptyList<FamilyMember>()
        result
    }
        .stateIn(
            scope = viewModelScope(),
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

//    override val members: StateFlow<List<FamilyMember>> =
//        combine(onlineUsers, localNames) { onlineUsers, localNames ->
//            onlineUsers.map { onlineUser ->
//
//                FamilyMember.createForOnlineUser(
//                    name = localNames.find { it.email == onlineUser.email }?.localName
//                        ?: onlineUser.name,
//                    onlineUser = onlineUser
//                )
//            }
//        }.stateIn(
//            scope = viewModelScope(),
//            started = SharingStarted.Lazily,
//            initialValue = emptyList()
//        )

    override val showLogoutDialog = MutableStateFlow(false)

    override fun onEvent(event: IProfileViewModel.Event) {
        when (event) {
            is IProfileViewModel.Event.Ordinal -> onOrdinalEvent(event)
            is IProfileViewModel.Event.AddUserDialog -> onAddUserDialogEvent(event)
        }
    }

    private fun onOrdinalEvent(event: IProfileViewModel.Event.Ordinal) {
        when (event) {
            is IProfileViewModel.Event.Ordinal.OnLogoutClick -> {
                showLogoutDialog.value = true
            }
            is IProfileViewModel.Event.Ordinal.OnLogoutAccepted -> longRunning {
                logoutUseCase(Unit)
                showLogoutDialog.value = false
                Unit
            }
            is IProfileViewModel.Event.Ordinal.OnLogoutDismiss -> {
                showLogoutDialog.value = false
            }
            is IProfileViewModel.Event.Ordinal.OnEditMemberClicked -> {
                editFamilyMemberText.value = event.familyMember.name
                editFamilyMemberSaveEnabled.value = event.familyMember.name.isValidUserName()
                editFamilyMemberDialog.value = event.familyMember
            }
            is IProfileViewModel.Event.Ordinal.OnEditMemberDismiss -> longRunning {
                closeEditMemberDialog()
            }
            is IProfileViewModel.Event.Ordinal.OnEditMemberTextChanged -> longRunning {
                editFamilyMemberText.value = event.text
                editFamilyMemberSaveEnabled.value = event.text.isValidUserName()
                Unit
            }
            is IProfileViewModel.Event.Ordinal.OnMemberLocalNameSaved -> longRunning {
                saveLocalNameUseCase(LocalName(event.email, event.localName))
                closeEditMemberDialog()
            }
            is IProfileViewModel.Event.Ordinal.OnExcludeDismiss -> {
                excludeFamilyMemberDialog.value = null
            }
            is IProfileViewModel.Event.Ordinal.OnExcludeFamilyMemberAccepted -> longRunning {
                excludeFamilyMemberUseCase(event.familyMember)
                updateCrutch()
                closeEditMemberDialog()
                excludeFamilyMemberDialog.value = null
                Unit
            }
            is IProfileViewModel.Event.Ordinal.OnExcludeFamilyMemberClick -> {
                excludeFamilyMemberDialog.value = event.familyMember
            }
            is IProfileViewModel.Event.Ordinal.ShowStatusMenu -> {
                changeStatusDialog.value = event.show
            }
            is IProfileViewModel.Event.Ordinal.ChangeStatus -> longRunning {
                updateStatusUseCase(event.status)
                updateCrutch()
                changeStatusDialog.value = false
                Unit
            }
            is IProfileViewModel.Event.Ordinal.OnProfileImageChanged -> longRunning {
                updateProfileImageUseCase(event.file)
                updateCrutch()
            }
            IProfileViewModel.Event.Ordinal.OnInviteFamilyMemberClicked -> {
                addUserDialogState.value = IProfileViewModel.Event.AddUserDialogState.createEmpty()
            }
        }
    }

    private fun onAddUserDialogEvent(event: IProfileViewModel.Event.AddUserDialog) {
        when (event) {
            is IProfileViewModel.Event.AddUserDialog.Close -> {
                addUserDialogState.value = null
            }
            is IProfileViewModel.Event.AddUserDialog.OnInputTextChanged -> {
                addUserDialogState.value = addUserDialogState.value?.copy(
                    textInput = event.text,
                    error = null
                )
            }
            is IProfileViewModel.Event.AddUserDialog.SendInvite -> longRunning {
                if (addUserDialogState.value?.loading == false) {

                    setAddUserDialogLoading(true)
                    val error = inviteFamilyMemberUseCase(event.email)
                    if (error == null) {
                        addUserDialogState.value = null
                    } else {
                        addUserDialogState.value = addUserDialogState.value?.copy(
                            loading = false,
                            error = error
                        )
                    }
                }
            }
        }
    }

    private fun setAddUserDialogLoading(loading: Boolean) {
        addUserDialogState.value = addUserDialogState.value?.copy(
            loading = loading
        )
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
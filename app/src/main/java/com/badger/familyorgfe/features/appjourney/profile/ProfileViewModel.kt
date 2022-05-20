package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.commoninteractors.GetMainUserUseCase
import com.badger.familyorgfe.data.model.LocalName
import com.badger.familyorgfe.ext.isValidName
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.profile.domain.ExcludeFamilyMemberUseCase
import com.badger.familyorgfe.features.appjourney.profile.domain.GetAllFamilyMembersUseCase
import com.badger.familyorgfe.features.appjourney.profile.domain.SaveLocalNameUseCase
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getMainUserUseCase: GetMainUserUseCase,
    private val getAllFamilyMembersUseCase: GetAllFamilyMembersUseCase,
    private val saveLocalNameUseCase: SaveLocalNameUseCase,
    private val excludeFamilyMemberUseCase: ExcludeFamilyMemberUseCase
) : BaseViewModel(), IProfileViewModel {

    override val mainUser: StateFlow<FamilyMember> = getMainUserUseCase(Unit)
        .map(FamilyMember::createForMainUser)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope(),
            started = SharingStarted.Lazily,
            initialValue = FamilyMember.createEmpty().copy(name = "Artem ")
        )

    override val editFamilyMemberDialog: MutableStateFlow<FamilyMember?> =
        MutableStateFlow(null)
    override val editFamilyMemberText: MutableStateFlow<String> =
        MutableStateFlow("")
    override val editFamilyMemberSaveEnabled: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    override val excludeFamilyMemberDialog: MutableStateFlow<FamilyMember?> =
        MutableStateFlow(null)

    override val members: StateFlow<List<FamilyMember>> = getAllFamilyMembersUseCase(Unit).stateIn(
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
            is IProfileViewModel.Event.OnLogoutAccepted -> {
                showLogoutDialog.value = false
            }
            is IProfileViewModel.Event.OnLogoutDismiss -> {
                showLogoutDialog.value = false
            }
            is IProfileViewModel.Event.OnEditMemberClicked -> {
                editFamilyMemberText.value = event.familyMember.name
                editFamilyMemberSaveEnabled.value = event.familyMember.name.isValidName()
                editFamilyMemberDialog.value = event.familyMember
            }
            is IProfileViewModel.Event.OnEditMemberDismiss -> longRunning {
                closeEditMemberDialog()
            }
            is IProfileViewModel.Event.OnEditMemberTextChanged -> longRunning {
                editFamilyMemberText.value = event.text
                editFamilyMemberSaveEnabled.value = event.text.isValidName()
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
                closeEditMemberDialog()
                excludeFamilyMemberDialog.value = null
                Unit
            }
            is IProfileViewModel.Event.OnExcludeFamilyMemberClick -> {
                excludeFamilyMemberDialog.value = event.familyMember
            }
        }
    }

    private fun updateUserFlow() = flow {
        val familyMembers = withContext(viewModelScope().coroutineContext) {
            getAllFamilyMembersUseCase(Unit)
        }
        emit(familyMembers)
    }

    private fun closeEditMemberDialog() {
        editFamilyMemberDialog.value = null
        editFamilyMemberText.value = ""
        editFamilyMemberSaveEnabled.value = false
    }

}
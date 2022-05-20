package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.commoninteractors.GetMainUserUseCase
import com.badger.familyorgfe.ext.viewModelScope
import com.badger.familyorgfe.features.appjourney.profile.domain.GetAllFamilyMembers
import com.badger.familyorgfe.features.appjourney.profile.model.FamilyMember
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getMainUserUseCase: GetMainUserUseCase,
    private val getAllFamilyMembers: GetAllFamilyMembers
) : BaseViewModel(), IProfileViewModel {

    override val mainUser: StateFlow<FamilyMember> = getMainUserUseCase(Unit)
        .map(FamilyMember::createForMainUser)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope(),
            started = SharingStarted.Lazily,
            initialValue = FamilyMember.createEmpty().copy(name = "Artem ")
        )

    override val members: StateFlow<List<FamilyMember>> = flow {
        val familyMembers = viewModelScope().async {
            getAllFamilyMembers(Unit)
        }.await()
        emit(familyMembers)
    }.stateIn(
        scope = viewModelScope(),
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )


    override val showLogoutDialog = MutableStateFlow(false)

    override fun onEvent(event: IProfileViewModel.Event) {
        when (event) {
            IProfileViewModel.Event.OnLogoutClick -> {
                showLogoutDialog.value = true
            }
            IProfileViewModel.Event.OnLogoutAccepted -> {
                showLogoutDialog.value = false
            }
            IProfileViewModel.Event.OnLogoutDismiss -> {
                showLogoutDialog.value = false
            }
        }
    }

}
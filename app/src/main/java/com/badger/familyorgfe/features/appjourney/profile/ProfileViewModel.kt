package com.badger.familyorgfe.features.appjourney.profile

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.domain.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : BaseViewModel(), IProfileViewModel {

    override fun clearData() = Unit
}
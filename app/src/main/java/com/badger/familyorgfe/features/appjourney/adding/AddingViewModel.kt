package com.badger.familyorgfe.features.appjourney.adding

import com.badger.familyorgfe.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddingViewModel @Inject constructor() : BaseViewModel(), IAddingViewModel {

    override fun clearData() = Unit
}
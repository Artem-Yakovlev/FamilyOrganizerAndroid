package com.badger.familyorgfe.features.appjourney.fridge

import com.badger.familyorgfe.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(): BaseViewModel(), IFridgeViewModel {

    override fun clearData() = Unit
}
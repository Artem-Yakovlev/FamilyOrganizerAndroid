package com.badger.familyorgfe.features.appjourney.adding.auto

import com.badger.familyorgfe.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
) : BaseViewModel(), IScannerViewModel {

    override fun onEvent(event: IScannerViewModel.Event) {
        when(event) {

        }
    }
}


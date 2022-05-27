package com.badger.familyorgfe.features.appjourney.products.scanner

import com.badger.familyorgfe.base.IBaseViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IScannerViewModel : IBaseViewModel<IScannerViewModel.Event> {

    val loading: StateFlow<Boolean>

    val failed: StateFlow<Boolean>

    val productsReceivedAction: SharedFlow<Unit>

    sealed class Event {
        data class CodeScanned(val code: String) : Event()
        object RetryScanning : Event()
        object Close : Event()
    }
}
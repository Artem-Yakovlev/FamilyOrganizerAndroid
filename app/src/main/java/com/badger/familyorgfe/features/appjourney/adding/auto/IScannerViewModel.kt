package com.badger.familyorgfe.features.appjourney.adding.auto

import com.badger.familyorgfe.base.IBaseViewModel
import com.badger.familyorgfe.data.model.Product
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IScannerViewModel : IBaseViewModel<IScannerViewModel.Event> {

    val loading: StateFlow<Boolean>

    val failed: StateFlow<Boolean>

    val productsReceivedAction: SharedFlow<List<Product>>

    sealed class Event {
        data class CodeScanned(val code: String) : Event()
        object RetryScanning : Event()
        object Close : Event()
    }
}
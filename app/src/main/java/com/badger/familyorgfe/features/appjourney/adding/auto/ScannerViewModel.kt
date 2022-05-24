package com.badger.familyorgfe.features.appjourney.adding.auto

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.data.model.Product
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.appjourney.adding.auto.domain.GetProductsByCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val getProductsByCodeUseCase: GetProductsByCodeUseCase
) : BaseViewModel(), IScannerViewModel {

    override val loading = MutableStateFlow(false)

    override val failed = MutableStateFlow(false)

    override val productsReceivedAction = MutableSharedFlow<List<Product>>(replay = 0)

    override fun onEvent(event: IScannerViewModel.Event) {
        when (event) {
            is IScannerViewModel.Event.CodeScanned -> longRunning {
                loading.value = true
                val result = getProductsByCodeUseCase(event.code)
                if (result.isNotEmpty()) {
                    productsReceivedAction.emit(result)
                } else {
                    loading.value = false
                    failed.value = true
                }
                Unit
            }
            is IScannerViewModel.Event.RetryScanning -> {
                loading.value = false
                failed.value = false
            }
            IScannerViewModel.Event.Close -> {
                clearData()
            }
        }
    }

    override fun clearData() {
        super.clearData()
        loading.value = false
        failed.value = false
    }
}


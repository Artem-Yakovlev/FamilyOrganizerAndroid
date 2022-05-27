package com.badger.familyorgfe.features.appjourney.products.scanner

import com.badger.familyorgfe.base.BaseViewModel
import com.badger.familyorgfe.ext.longRunning
import com.badger.familyorgfe.features.appjourney.products.adding.repository.IAddingRepository
import com.badger.familyorgfe.features.appjourney.products.scanner.domain.GetProductsByCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val addingRepository: IAddingRepository,
    private val getProductsByCodeUseCase: GetProductsByCodeUseCase
) : BaseViewModel(), IScannerViewModel {

    override val loading = MutableStateFlow(false)

    override val failed = MutableStateFlow(false)

    override val productsReceivedAction = MutableSharedFlow<Unit>(replay = 0)

    override fun onEvent(event: IScannerViewModel.Event) {
        when (event) {
            is IScannerViewModel.Event.CodeScanned -> longRunning {
                if (!loading.value) {
                    loading.value = true
                    val result = getProductsByCodeUseCase(event.code)
                    if (result.isNotEmpty()) {
                        addingRepository.addProducts(*result.toTypedArray())
                        productsReceivedAction.emit(Unit)
                    } else {
                        loading.value = false
                        failed.value = true
                    }
                }
                Unit
            }
            is IScannerViewModel.Event.RetryScanning -> {
                clearData()
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


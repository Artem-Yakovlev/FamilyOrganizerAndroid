package com.badger.familyorgfe.features.appjourney.adding

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.features.appjourney.adding.auto.IScannerViewModel
import com.badger.familyorgfe.features.appjourney.adding.auto.ScannerScreen
import com.badger.familyorgfe.features.appjourney.adding.auto.ScannerViewModel
import com.badger.familyorgfe.features.appjourney.adding.manual.AddingScreen
import com.badger.familyorgfe.features.appjourney.adding.manual.AddingViewModel
import com.badger.familyorgfe.features.appjourney.adding.manual.IAddingViewModel

@Composable
fun AddingJourney(
    modifier: Modifier,
    navOnBack: () -> Unit,
    addingViewModel: IAddingViewModel = hiltViewModel<AddingViewModel>(),
    scannerViewModel: IScannerViewModel = hiltViewModel<ScannerViewModel>()
) {
    var manual by remember { mutableStateOf(true) }

    if (manual) {
        AddingScreen(
            modifier = modifier,
            navOnBack = navOnBack,
            viewModel = addingViewModel,
            openQrScanner = {
                scannerViewModel.onEvent(IScannerViewModel.Event.Close)
                manual = false
            }
        )
    } else {
        ScannerScreen(
            modifier = modifier,
            navOnBack = { manual = true },
            viewModel = scannerViewModel,
            productsReceived = { products ->
                addingViewModel.onEvent(
                    IAddingViewModel.Event.Ordinal.OnProductsScanned(products)
                )
                manual = true
            }
        )
    }
}

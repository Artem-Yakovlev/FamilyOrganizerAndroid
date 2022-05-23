package com.badger.familyorgfe.features.appjourney.adding.manual

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.badger.familyorgfe.features.appjourney.adding.auto.IScannerViewModel
import com.badger.familyorgfe.features.appjourney.adding.auto.ScannerScreen
import com.badger.familyorgfe.features.appjourney.adding.auto.ScannerViewModel

@Composable
fun AddingJourney(
    modifier: Modifier,
    navOnBack: () -> Unit,
    addingViewModel: IAddingViewModel = hiltViewModel<AddingViewModel>(),
    scannerViewModel: IScannerViewModel = hiltViewModel<ScannerViewModel>()
) {
    var manual by remember { mutableStateOf(false) }

    if (manual) {
        AddingScreen(
            modifier = modifier,
            navOnBack = navOnBack,
            viewModel = addingViewModel
        )
    } else {
        ScannerScreen(
            modifier = modifier,
            navOnBack = {
                manual = true
            },
            viewModel = scannerViewModel
        )
    }
}

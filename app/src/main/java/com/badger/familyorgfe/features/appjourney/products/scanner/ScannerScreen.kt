package com.badger.familyorgfe.features.appjourney.products.scanner

import android.Manifest
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badger.familyorgfe.R
import com.badger.familyorgfe.features.appjourney.products.scanner.domain.QrCodeAnalyzer
import com.badger.familyorgfe.ui.elements.FullScreenLoading
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.utils.BackHandler
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.flow.collectLatest

private const val CAMERA_PERMISSION = Manifest.permission.CAMERA

@Composable
fun ScannerScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: IScannerViewModel = hiltViewModel<ScannerViewModel>()
) {
    val onBack: () -> Unit = {
        navController.popBackStack()
        viewModel.onEvent(IScannerViewModel.Event.RetryScanning)
    }
    BackHandler(onBack = onBack)

    val cameraPermissionState = rememberPermissionState(CAMERA_PERMISSION)
    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
        viewModel.productsReceivedAction.collectLatest {
            navController.popBackStack()
            viewModel.onEvent(IScannerViewModel.Event.RetryScanning)
        }
    }

    when (cameraPermissionState.status) {
        is PermissionStatus.Granted -> {
            val loading by viewModel.loading.collectAsState()
            val failed by viewModel.failed.collectAsState()

            Screen(
                loading = loading,
                failed = failed,
                codeScanned = { code ->
                    viewModel.onEvent(
                        IScannerViewModel.Event.CodeScanned(code)
                    )
                },
                onRetryClicked = {
                    viewModel.onEvent(
                        IScannerViewModel.Event.RetryScanning
                    )
                }
            )
        }
        is PermissionStatus.Denied -> {
            PermissionScreen(
                status = cameraPermissionState.status,
                onPermissionButtonClicked = { cameraPermissionState.launchPermissionRequest() }
            )
        }
    }
}

@Composable
private fun PermissionScreen(
    status: PermissionStatus,
    onPermissionButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.qr_scanning_permission_title),
            style = FamilyOrganizerTheme.textStyle.headline2,
            lineHeight = 26.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (status.shouldShowRationale) {
                stringResource(R.string.qr_scanning_permission_description_ration)
            } else {
                stringResource(R.string.qr_scanning_permission_description_denied)
            },
            style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                fontSize = 14.sp,
                color = FamilyOrganizerTheme.colors.darkClay
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (status.shouldShowRationale) {
            Button(
                onClick = onPermissionButtonClicked,
                colors = buttonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = stringResource(R.string.qr_scanning_permission_button).uppercase(),
                    color = FamilyOrganizerTheme.colors.whitePrimary,
                    style = FamilyOrganizerTheme.textStyle.button,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun Screen(
    loading: Boolean,
    failed: Boolean,
    codeScanned: (String) -> Unit,
    onRetryClicked: () -> Unit
) {
    when {
        loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FamilyOrganizerTheme.colors.whitePrimary)
            ) {
                FullScreenLoading(
                    modifier = Modifier.fillMaxSize(),
                    backgroundAlpha = 1f
                ) {}

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = stringResource(R.string.qr_scanning_loading_title),
                        style = FamilyOrganizerTheme.textStyle.headline2,
                        lineHeight = 26.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.qr_scanning_loading_description),
                        style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                            fontSize = 14.sp,
                            color = FamilyOrganizerTheme.colors.darkClay
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
        failed -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FamilyOrganizerTheme.colors.whitePrimary)
            ) {

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = stringResource(R.string.qr_scanning_failed_title),
                        style = FamilyOrganizerTheme.textStyle.headline2,
                        lineHeight = 26.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.qr_scanning_failed_description),
                        style = FamilyOrganizerTheme.textStyle.subtitle2.copy(
                            fontSize = 14.sp,
                            color = FamilyOrganizerTheme.colors.darkClay
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onRetryClicked,
                        enabled = true,
                        colors = buttonColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = stringResource(R.string.qr_scanning_failed_button).uppercase(),
                            color = FamilyOrganizerTheme.colors.whitePrimary,
                            style = FamilyOrganizerTheme.textStyle.button,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                }
            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraScreen(codeScanned)

                Column {
                    BlackoutLine(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Row(modifier = Modifier.weight(1f)) {
                        BlackoutLine(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.weight(3f))

                        BlackoutLine(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                    }
                    BlackoutLine(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BlackoutLine(
    modifier: Modifier
) {
    Surface(
        modifier = modifier,
        color = FamilyOrganizerTheme.colors.blackPrimary.copy(alpha = 0.35f),
    ) {}
}

@Composable
private fun CameraScreen(
    codeScanned: (String) -> Unit
) {
    val localContext = LocalContext.current
    val localLifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }

    AndroidView(
        factory = { context ->

            val previewView = PreviewView(context)
            val executor = ContextCompat.getMainExecutor(context)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also { preview ->
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                }
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(
                        Size(
                            previewView.width,
                            previewView.height
                        )
                    )
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QrCodeAnalyzer(codeScanned)
                )

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    localLifecycleOwner,
                    createBackCameraSelector(),
                    preview,
                    imageAnalysis
                )
            }, executor)
            previewView
        }, modifier = Modifier.fillMaxSize()
    )
}

private fun createBackCameraSelector() = CameraSelector.Builder()
    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
    .build()
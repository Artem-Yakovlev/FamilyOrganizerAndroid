package com.badger.familyorgfe.features.appjourney.adding.auto

import android.Manifest
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.badger.familyorgfe.R
import com.badger.familyorgfe.ui.style.buttonColors
import com.badger.familyorgfe.ui.theme.FamilyOrganizerTheme
import com.badger.familyorgfe.utils.BackHandler
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

private const val CAMERA_PERMISSION = Manifest.permission.CAMERA

@Composable
fun ScannerScreen(
    modifier: Modifier,
    navOnBack: () -> Unit,
    viewModel: IScannerViewModel
) {
    val onBack: () -> Unit = {
        navOnBack()
    }
    BackHandler(onBack = onBack)

    val cameraPermissionState = rememberPermissionState(CAMERA_PERMISSION)

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    when (cameraPermissionState.status) {
        is PermissionStatus.Granted -> {
            Screen()
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
private fun Screen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraScreen()

        Column {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = FamilyOrganizerTheme.colors.blackPrimary.copy(alpha = 0.35f),
                content = { }
            )

            Row(modifier = Modifier.weight(1f)) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    color = FamilyOrganizerTheme.colors.blackPrimary.copy(alpha = 0.35f),
                    content = { }
                )
                Spacer(modifier = Modifier.weight(3f))

                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    color = FamilyOrganizerTheme.colors.blackPrimary.copy(alpha = 0.35f),
                    content = { }
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = FamilyOrganizerTheme.colors.blackPrimary.copy(alpha = 0.35f),
                content = { }
            )
        }
    }
}

@Composable
private fun CameraScreen() {
    val localContext = LocalContext.current
    val localLifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }
    var code by remember {
        mutableStateOf("")
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
                    QrCodeAnalyzer { result ->
                        code = result
                    }
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
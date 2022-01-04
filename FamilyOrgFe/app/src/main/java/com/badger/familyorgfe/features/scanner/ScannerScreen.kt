package com.badger.familyorgfe.features.scanner

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

@Composable
fun ScannerScreen() {

    var code by remember {
        mutableStateOf("")
    }

    val localContext = LocalContext.current
    val localLifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            factory = { context ->
                PreviewView(context).apply {
                    val preview = Preview.Builder().build()
                    preview.setSurfaceProvider(surfaceProvider)


                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                this.width,
                                this.height
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            code = result
                        }
                    )

                    cameraProviderFuture.get()
                        .bindToLifecycle(localLifecycleOwner, createBackCameraSelector(), preview, imageAnalysis)
                }
            }, modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = code)
    }
}

private fun createBackCameraSelector() = CameraSelector.Builder()
    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
    .build()
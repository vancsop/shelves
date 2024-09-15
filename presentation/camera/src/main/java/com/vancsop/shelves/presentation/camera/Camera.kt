package com.vancsop.shelves.presentation.camera

import android.graphics.Bitmap
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.vancsop.shelves.presentation.design.ShelvesTheme

@Composable
fun Camera(
    state: State<CameraViewModel.State>,
    onAddPress: (Bitmap) -> Unit,
    onGalleryPress: () -> Unit,
    onShelfIdChange: (String) -> Unit,
    analyzer: Analyzer
) {
    val applicationContext = LocalContext.current.applicationContext
    ShelvesTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = onGalleryPress,
                    icon = { Icon(Icons.Filled.Menu, stringResource(id = R.string.camera_catalogue)) },
                    text = { Text(text = stringResource(id = R.string.camera_catalogue)) },
                )
            }
        ) { innerPadding ->
            val imageAnalyzer = remember { analyzer }
            val controller = remember {
                LifecycleCameraController(applicationContext).apply {
                    setImageAnalysisAnalyzer(
                        ContextCompat.getMainExecutor(applicationContext),
                        imageAnalyzer
                    )
                }
            }
            val cameraAction = remember {
                CameraAction(applicationContext, controller, onAddPress)
            }
            when (state.value) {
                is CameraViewModel.State.Classifying,
                is CameraViewModel.State.Classified -> {
                    CameraPreview(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        controller = controller
                    )
                }
                else -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.camera_shelf_id_hint),
                        color = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = state.value.shelfId,
                    singleLine = true,
                    onValueChange = onShelfIdChange,
                    label = { Text(stringResource(id = R.string.camera_shelf_id_label)) }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                (state.value as? CameraViewModel.State.Classified)?.let {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 64.dp, end = 64.dp),
                        onClick = { cameraAction() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = it.classification,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(text = it.classification)
                    }
                }
            }
        }
    }
}

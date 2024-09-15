package com.vancsop.shelves

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vancsop.shelves.data.classifier.Analyzer
import com.vancsop.shelves.data.classifier.TFClassifier
import com.vancsop.shelves.presentation.camera.Camera
import com.vancsop.shelves.presentation.camera.CameraViewModel
import com.vancsop.shelves.presentation.gallery.Gallery
import com.vancsop.shelves.presentation.gallery.GalleryViewModel
import kotlinx.serialization.Serializable

@Composable
fun MainNavigation() {
    val applicationContext = LocalContext.current.applicationContext
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenCamera
    ) {
        composable<ScreenCamera> {
            hiltViewModel<CameraViewModel>().run {
                val analyzer = remember {
                    Analyzer(
                        classifier = TFClassifier(applicationContext),
                        onResult = ::onCameraResult
                    )
                }
                Camera(
                    state = state.collectAsState(),
                    onAddPress = ::onAddPress,
                    onGalleryPress = { navController.navigate(ScreenGallery) },
                    onShelfIdChange = ::onShelfIdChange,
                    analyzer
                )
            }
        }
        composable<ScreenGallery> {
            hiltViewModel<GalleryViewModel>().run {
                Gallery(
                    state = state.collectAsState(),
                    onImagePress = ::onImagePress,
                    onExportPress = ::onExportPress
                )
            }
        }
    }
}

@Serializable
object ScreenCamera

@Serializable
object ScreenGallery

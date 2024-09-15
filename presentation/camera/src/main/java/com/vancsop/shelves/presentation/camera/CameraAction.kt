package com.vancsop.shelves.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat

class CameraAction(
    private val applicationContext: Context,
    private val controller: LifecycleCameraController,
    val onPictureTaken: (Bitmap) -> Unit
): OnImageCapturedCallback() {
    operator fun invoke() {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            this
        )
    }

    override fun onCaptureSuccess(image: ImageProxy) {
        super.onCaptureSuccess(image)

        onPictureTaken(image.toBitmap().rotate(image.imageInfo.rotationDegrees.toFloat()))
    }
}

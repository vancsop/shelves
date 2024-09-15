package com.vancsop.shelves.data.classifier

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.vancsop.shelves.data.core.Classifier

class Analyzer(
    private val classifier: Classifier,
    private val onResult: (String) -> Unit
): ImageAnalysis.Analyzer {
    private var skippedFrames = 0

    override fun analyze(image: ImageProxy) {
        if (skippedFrames % COMMON_FRAME_RATE == 0) {
            val rotationDeg = image.imageInfo.rotationDegrees
            val bitmap = image.toBitmap().centerCrop(DESIRED_SIZE, DESIRED_SIZE)
            val result = classifier.classify(bitmap, rotationDeg)
            result.firstOrNull()?.classification?.let { onResult(it) }
        }

        skippedFrames++
        image.close()
    }

    companion object {
        private const val DESIRED_SIZE = 321
        private const val COMMON_FRAME_RATE = 60
    }
}

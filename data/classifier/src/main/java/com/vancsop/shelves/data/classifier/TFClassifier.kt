package com.vancsop.shelves.data.classifier

import android.content.Context
import android.graphics.Bitmap
import android.view.Surface
import com.vancsop.shelves.data.core.Classifier
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class TFClassifier(
    private val context: Context,
    private val threshold: Float = 0.5F,
    private val maxResult: Int = 1
): Classifier {
    private var classifier: ImageClassifier? = null

    private fun createClassifier(): ImageClassifier? {
        val baseOptions = BaseOptions.builder()
            .setNumThreads(2)
            .build()
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setBaseOptions(baseOptions)
            .setMaxResults(maxResult)
            .setScoreThreshold(threshold)
            .build()

        return try {
            ImageClassifier.createFromFileAndOptions(
                context,
                "efficientnet-lite0-fp32.tflite",
                options
            )
        } catch (e: IllegalStateException) {
            null
        }
    }

    private fun orientationOf(rotation: Int): ImageProcessingOptions.Orientation {
        return when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }

    override fun classify(bitmap: Bitmap, rotation: Int): List<Classifier.Result> {
        if (classifier == null) {
            classifier = createClassifier()
        }

        val imageProcessor = ImageProcessor.Builder().build()
        val image = imageProcessor.process(TensorImage.fromBitmap(bitmap))
        val imagerProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(orientationOf(rotation))
            .build()
        val result = classifier?.classify(image, imagerProcessingOptions).orEmpty()

        return result.flatMap { classifications ->
            classifications.categories.map { category ->
                Classifier.Result(
                    classification = category.label,
                    score = category.score
                )
            }
        }.distinctBy { it.classification }
    }

}

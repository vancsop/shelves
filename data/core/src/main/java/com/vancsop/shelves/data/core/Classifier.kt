package com.vancsop.shelves.data.core

import android.graphics.Bitmap

interface Classifier {
    fun classify(bitmap: Bitmap, rotation: Int): List<Result>

    data class Result(
        val classification: String,
        val score: Float
    )
}

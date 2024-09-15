package com.vancsop.shelves.data.classifier

import android.graphics.Bitmap

internal fun Bitmap.centerCrop(
    desiredWidth: Int,
    desiredHeight: Int
): Bitmap {
    val xStart = (width - desiredWidth) / 2
    val yStart = (height - desiredHeight) / 2

    if (xStart < 0 || yStart < 0 || desiredWidth > width || desiredHeight > height) {
        throw IllegalArgumentException("$width x $height -> $desiredWidth x $desiredHeight")
    }

    return Bitmap.createBitmap(this, xStart, yStart, desiredWidth, desiredHeight)
}
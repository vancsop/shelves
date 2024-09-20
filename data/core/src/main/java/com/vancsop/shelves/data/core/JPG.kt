package com.vancsop.shelves.data.core

import android.graphics.Bitmap

interface JPG {
    suspend fun createFrom(bitmap: Bitmap): Result<String>
}

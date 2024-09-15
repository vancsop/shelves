package com.vancsop.shelves.data.files

import android.graphics.Bitmap
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID
import javax.inject.Inject

class JPG @Inject constructor() {
    suspend fun createFrom(bitmap: Bitmap): Result<String> {
        val fileName = "${UUID.randomUUID()}.jpg"
        val dir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        ).path.plus("/shelves")
        val path = dir.plus("/$fileName")

        return try {
            withContext(Dispatchers.IO) {
                Files.createDirectories(Paths.get(dir))
                val fos = FileOutputStream(path)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()
            }
            Result.success(path)
        } catch (expected: Exception) {
            Timber.e(expected)
            Result.failure(expected)
        }
    }
}

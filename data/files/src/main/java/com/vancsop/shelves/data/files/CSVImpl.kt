package com.vancsop.shelves.data.files

import android.os.Environment
import com.vancsop.shelves.data.core.CSV
import com.vancsop.shelves.data.core.CatalogueRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class CSVImpl @Inject constructor(): CSV {
    override suspend fun createFrom(items: List<CatalogueRepo.Item>): Result<String> {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .path.plus("/export_shelves_${System.currentTimeMillis()}.csv")

        return try {
            withContext(Dispatchers.IO) {
                FileOutputStream(path).writeCsv(items)
            }
            Timber.d("exported: $path")
            Result.success(path)
        } catch (expected: Exception) {
            Timber.e(expected)
            Result.failure(expected)
        }
    }

    private fun OutputStream.writeCsv(items: List<CatalogueRepo.Item>) {
        val writer = bufferedWriter()
        writer.write(""""classification", "shelfId"""")
        writer.newLine()
        items.forEach {
            writer.write("${it.classification}, ${it.shelfId}")
            writer.newLine()
        }
        writer.flush()
    }
}

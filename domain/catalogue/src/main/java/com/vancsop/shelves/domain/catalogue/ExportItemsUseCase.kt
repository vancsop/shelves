package com.vancsop.shelves.domain.catalogue

import com.vancsop.shelves.data.core.CatalogueRepo
import com.vancsop.shelves.data.files.CSV
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import timber.log.Timber
import javax.inject.Inject

class ExportItemsUseCase @Inject constructor(
    private val repo: CatalogueRepo,
    private val csv: CSV
) {
    suspend operator fun invoke(): Result<String> {
        Timber.d("export")
        val items = repo.getItems().take(1).first()
        return csv.createFrom(items)
    }
}

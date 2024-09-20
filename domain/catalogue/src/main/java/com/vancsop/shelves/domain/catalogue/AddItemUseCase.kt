package com.vancsop.shelves.domain.catalogue

import android.graphics.Bitmap
import com.vancsop.shelves.data.core.CatalogueRepo
import com.vancsop.shelves.data.core.JPG
import javax.inject.Inject

class AddItemUseCase @Inject constructor(
    private val repo: CatalogueRepo,
    private val jpg: JPG
) {
    suspend operator fun invoke(
        classification: String,
        shelfId: String,
        bitmap: Bitmap
    ): Boolean {
        val result = jpg.createFrom(bitmap).map {
            CatalogueRepo.Item(
                classification = classification,
                shelfId = shelfId,
                path = it
            )
        }.onSuccess { item ->
            repo.add(item)
        }

        return result.isSuccess
    }
}

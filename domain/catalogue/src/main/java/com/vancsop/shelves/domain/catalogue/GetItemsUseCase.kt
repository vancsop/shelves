package com.vancsop.shelves.domain.catalogue

import com.vancsop.shelves.data.core.CatalogueRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repo: CatalogueRepo
) {
    operator fun invoke(): Flow<List<Item>> = repo.getItems().map {
        it.map { item ->
            Item(
                classification = item.classification,
                shelfId = item.shelfId,
                path = item.path
            )
        }
    }

    data class Item(
        val classification: String,
        val shelfId: String,
        val path: String
    )
}

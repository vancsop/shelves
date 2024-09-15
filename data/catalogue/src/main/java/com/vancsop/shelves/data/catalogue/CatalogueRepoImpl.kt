package com.vancsop.shelves.data.catalogue

import com.vancsop.shelves.data.core.CatalogueRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatalogueRepoImpl @Inject constructor(
    private val catalogueDao: CatalogueDao
): CatalogueRepo {
    override suspend fun add(item: CatalogueRepo.Item) {
        catalogueDao.insertCatalogueItem(
            CatalogItemEntity(
                classification = item.classification,
                shelfId = item.shelfId,
                path = item.path
            )
        )
    }

    override fun getItems(): Flow<List<CatalogueRepo.Item>> =
        catalogueDao.getCatalogueItems().map {
            it.map { entity ->
                CatalogueRepo.Item(
                    classification = entity.classification,
                    shelfId = entity.shelfId,
                    path = entity.path
                )
            }
        }
}

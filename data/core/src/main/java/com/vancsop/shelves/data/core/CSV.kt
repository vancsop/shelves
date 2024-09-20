package com.vancsop.shelves.data.core

interface CSV {
    suspend fun createFrom(items: List<CatalogueRepo.Item>): Result<String>
}

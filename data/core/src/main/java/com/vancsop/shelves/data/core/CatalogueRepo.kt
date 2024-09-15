package com.vancsop.shelves.data.core

import kotlinx.coroutines.flow.Flow

interface CatalogueRepo {
    suspend fun add(item: Item)
    fun getItems(): Flow<List<Item>>

    data class Item(val shelfId: String, val classification: String, val path: String)
}

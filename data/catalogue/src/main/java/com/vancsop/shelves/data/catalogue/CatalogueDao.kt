package com.vancsop.shelves.data.catalogue

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatalogueItem(catalogueEntity: CatalogItemEntity): Long
    @Query(value = "SELECT * FROM catalogue")
    fun getCatalogueItems(): Flow<List<CatalogItemEntity>>
}

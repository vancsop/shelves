package com.vancsop.shelves.data.catalogue

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CatalogItemEntity::class], version = 1, exportSchema = false)
abstract class CatalogueDatabase: RoomDatabase() {
    abstract fun catalogueDao(): CatalogueDao
}

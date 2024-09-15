package com.vancsop.shelves.data.catalogue

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "catalogue")
data class CatalogItemEntity(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    @ColumnInfo("shelf_id") var shelfId: String = "",
    @ColumnInfo("classification") var classification: String = "",
    @ColumnInfo("path") var path: String = "",
    @ColumnInfo("time_millis") var timeMillis: Long = System.currentTimeMillis(),
)

package com.vancsop.shelves.data.catalogue

import android.content.Context
import androidx.room.Room
import com.vancsop.shelves.data.core.CatalogueRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CatalogueModule {
    @Provides
    fun provideCatalogueDatabase(
        @ApplicationContext context: Context
    ): CatalogueDatabase = Room.databaseBuilder(
        context,
        CatalogueDatabase::class.java,
        "catalogue.db"
    ).build()

    @Provides
    fun provideCatalogueDao(
        database: CatalogueDatabase
    ): CatalogueDao = database.catalogueDao()

    @Provides
    fun provideCatalogueRepo(
        catalogueDao: CatalogueDao
    ): CatalogueRepo = CatalogueRepoImpl(catalogueDao)
}

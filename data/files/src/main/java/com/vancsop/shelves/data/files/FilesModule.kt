package com.vancsop.shelves.data.files

import com.vancsop.shelves.data.core.CSV
import com.vancsop.shelves.data.core.JPG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FilesModule {
    @Provides
    fun provideCSV(): CSV = CSVImpl()
    @Provides
    fun provideJPG(): JPG = JPGImpl()
}

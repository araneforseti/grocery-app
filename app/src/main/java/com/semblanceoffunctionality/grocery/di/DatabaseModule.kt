package com.semblanceoffunctionality.grocery.di

import android.content.Context
import com.semblanceoffunctionality.grocery.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideItemDao(appDatabase: AppDatabase): ItemDao {
        return appDatabase.itemDao()
    }

    @Provides
    fun provideStoreDao(appDatabase: AppDatabase): StoreDao {
        return appDatabase.storeDao()
    }

    @Provides
    fun provideStockStatusDao(appDatabase: AppDatabase): StockStatusDao {
        return appDatabase.stockStatusDao()
    }

    @Provides
    fun provideStockStatusGroceryItemForStoreDao(appDatabase: AppDatabase): StockStatusGroceryItemForStoreDao {
        return appDatabase.stockStatusGroceryItemForStoreDao()
    }
}

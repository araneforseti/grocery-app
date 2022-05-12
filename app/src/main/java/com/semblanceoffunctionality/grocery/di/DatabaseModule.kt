package com.semblanceoffunctionality.grocery.di

import android.content.Context
import com.semblanceoffunctionality.grocery.data.AppDatabase
import com.semblanceoffunctionality.grocery.data.ItemDao
import com.semblanceoffunctionality.grocery.data.StoreDao
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
}

package com.semblanceoffunctionality.groceryapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.semblanceoffunctionality.groceryapp.utilities.DATABASE_NAME

@Database(entities = [GroceryItem::class,
    Store::class,
    StoreStock::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groceryItemDao(): GroceryItemDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}
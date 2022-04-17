package com.semblanceoffunctionality.groceryapp.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["store", "item"],
        foreignKeys = arrayOf(
            ForeignKey(entity = Store::class,
                       parentColumns = arrayOf("name"),
                       childColumns = arrayOf("store")),
            ForeignKey(entity = GroceryItem::class,
            parentColumns = arrayOf("name"),
            childColumns = arrayOf("item"))
        ))
data class StoreStock(
    val store: Store,
    val item: GroceryItem,
    val present: Boolean
)

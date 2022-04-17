package com.semblanceoffunctionality.groceryapp.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["store", "item"])
data class StoreStock(
    val store: String,
    val item: String,
    val present: Boolean
)

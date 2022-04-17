package com.semblanceoffunctionality.groceryapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroceryItem(
    @PrimaryKey val name: String,
    val wanted: Boolean
)

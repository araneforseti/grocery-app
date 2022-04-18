package com.semblanceoffunctionality.groceryapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Store(
    @PrimaryKey val name: String,
    val address: String
)

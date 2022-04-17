package com.semblanceoffunctionality.groceryapp.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class StoreWithItems (
    @Embedded val store: Store,
    @Relation(
        parentColumn = "name",
        entityColumn = "name",
        associateBy = Junction(StoreStock::class)
    )
    val items: List<GroceryItem>
)
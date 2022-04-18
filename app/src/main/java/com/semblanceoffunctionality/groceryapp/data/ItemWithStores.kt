package com.semblanceoffunctionality.groceryapp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class ItemWithStores(
    @Embedded val item: GroceryItem,
    @Relation(
        parentColumn = "name",
        entityColumn = "name",
        associateBy = Junction(StoreStock::class)
    )
    val stores: List<Store>
)

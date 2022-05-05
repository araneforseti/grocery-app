package com.semblanceoffunctionality.grocery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey @ColumnInfo(name = "id") val itemId: String,
    val name: String,
    val wanted: Boolean = true
) {
    override fun toString() = name
}

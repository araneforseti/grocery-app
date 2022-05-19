package com.semblanceoffunctionality.grocery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    val wanted: Boolean = true,
    val quantity: Int = 0,
    val obtained: Boolean = false
) {
    override fun toString() = name
}

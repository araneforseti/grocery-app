package com.semblanceoffunctionality.grocery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class Store(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    val address: String,
) {
    override fun toString() = name
}

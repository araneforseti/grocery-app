package com.semblanceoffunctionality.grocery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class Store(
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    var address: String,
) {
    override fun toString() = name
}

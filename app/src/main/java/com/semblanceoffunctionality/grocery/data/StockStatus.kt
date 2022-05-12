package com.semblanceoffunctionality.grocery.data

import androidx.room.*

@TypeConverters(StockConverters::class)
@Entity(
    tableName = "stock_status",
    foreignKeys = [
        ForeignKey(entity = Item::class, parentColumns = ["id"], childColumns = ["itemId"]),
        ForeignKey(entity = Store::class, parentColumns = ["name"], childColumns = ["store"])
    ],
    indices = [Index("itemId", "store")],
    primaryKeys = ["itemId", "store"]
)
data class StockStatus(
    @ColumnInfo(name="itemId") val itemId: String,
    @ColumnInfo(name="store") val store: String,
    @ColumnInfo(name="stockStatus") val stockStatus: StockStatusEnum
) {}
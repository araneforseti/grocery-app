package com.semblanceoffunctionality.grocery.data

import androidx.room.*

@TypeConverters(StockConverters::class)
@Entity(
    tableName = "stock_status",
    foreignKeys = [
        ForeignKey(entity = Item::class, parentColumns = ["name"], childColumns = ["item"]),
        ForeignKey(entity = Store::class, parentColumns = ["name"], childColumns = ["store"])
    ],
    indices = [Index("item", "store")],
    primaryKeys = ["item", "store"]
)
data class StockStatus(
    @ColumnInfo(name="item") var item: String,
    @ColumnInfo(name="store") var store: String,
    @ColumnInfo(name="stockStatus") var stockStatus: StockStatusEnum
) {
    fun isStocked(): Boolean {
        return stockStatus == StockStatusEnum.STOCKED
    }

    fun isUnknown(): Boolean {
        return stockStatus == StockStatusEnum.UNKNOWN
    }

    fun isNotStocked(): Boolean {
        return stockStatus == StockStatusEnum.NOT_STOCKED
    }
}
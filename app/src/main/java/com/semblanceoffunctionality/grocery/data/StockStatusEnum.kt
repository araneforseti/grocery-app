package com.semblanceoffunctionality.grocery.data

import androidx.room.TypeConverter

enum class StockStatusEnum(val value: Int) {
    STOCKED(0),
    UNKNOWN(1),
    NOT_STOCKED(2)
}

class StockConverters {
    @TypeConverter
    fun toStockStatus(value: Int) = enumValues<StockStatusEnum>()[value]

    @TypeConverter
    fun fromStockStatus(value: StockStatusEnum) = value.ordinal
}
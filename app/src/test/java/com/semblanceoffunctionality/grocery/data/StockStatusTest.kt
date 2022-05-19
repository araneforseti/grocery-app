package com.semblanceoffunctionality.grocery.data

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StockStatusTest {
    private lateinit var stockStatusStocked: StockStatus
    private lateinit var stockStatusUnknown: StockStatus
    private lateinit var stockStatusNotStocked: StockStatus

    @Before
    fun setUp() {
        stockStatusStocked = StockStatus(
            "item",
            "store",
            StockStatusEnum.STOCKED
        )
        stockStatusUnknown = StockStatus(
            "item",
            "store",
            StockStatusEnum.UNKNOWN
        )
        stockStatusNotStocked = StockStatus(
            "item",
            "store",
            StockStatusEnum.NOT_STOCKED
        )
    }

    @Test
    fun test_isStocked_returnsFalse_whenUnknown() {
        Assert.assertEquals(false, stockStatusUnknown.isStocked())
    }

    @Test
    fun test_isUnknownReturnsTrue_whenUnknown() {
        Assert.assertEquals(true, stockStatusUnknown.isUnknown())
    }

    @Test
    fun test_isNotStocked_returnsFalse_whenUnknown() {
        Assert.assertEquals(false, stockStatusUnknown.isNotStocked())
    }

    @Test
    fun test_isStocked_returnsTrue_whenStocked() {
        Assert.assertEquals(true, stockStatusStocked.isStocked())
    }

    @Test
    fun test_isUnknownReturnsFalse_whenStocked() {
        Assert.assertEquals(false, stockStatusStocked.isUnknown())
    }

    @Test
    fun test_isNotStocked_returnsFalse_whenStocked() {
        Assert.assertEquals(false, stockStatusStocked.isNotStocked())
    }

    @Test
    fun test_isStocked_returnsFalse_whenNotStocked() {
        Assert.assertEquals(false, stockStatusNotStocked.isStocked())
    }

    @Test
    fun test_isUnknownReturnsFalse_whenNotStocked() {
        Assert.assertEquals(false, stockStatusNotStocked.isUnknown())
    }

    @Test
    fun test_isNotStocked_returnsTrue_whenNotStocked() {
        Assert.assertEquals(true, stockStatusNotStocked.isNotStocked())
    }
}
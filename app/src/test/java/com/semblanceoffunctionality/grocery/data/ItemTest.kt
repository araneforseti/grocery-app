package com.semblanceoffunctionality.grocery.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ItemTest {

    private lateinit var item: Item

    @Before fun setUp() {
        item = Item("Tomato")
    }

    @Test fun test_toString() {
        assertEquals("Tomato", item.toString())
    }
}

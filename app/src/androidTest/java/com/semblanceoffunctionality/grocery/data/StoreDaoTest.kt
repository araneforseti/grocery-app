package com.semblanceoffunctionality.grocery.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StoreDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var storeDao: StoreDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        storeDao = database.storeDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetStores_whenNone() = runBlocking {
        val itemList = storeDao.getStores().first()
        assertThat(itemList.size, equalTo(0))
    }


    @Test
    fun testGetStores_whenSome() = runBlocking {
        val store1 = Store("Store A","")
        val store2 = Store("Store B","")
        storeDao.insertAll(listOf(store1, store2))

        val storeList = storeDao.getStores().first()
        assertThat(storeList.size, equalTo(2))
        assertThat(storeList[0].name, equalTo(store1.name))
        assertThat(storeList[1].name, equalTo(store2.name))
    }

    @Test
    fun testGetStore() = runBlocking {
        val store1 = Store("Store A","")
        val store2 = Store("Store B","")
        storeDao.insertAll(listOf(store1, store2))
        val store = storeDao.getStore(store1.name).first()

        assertThat(store.name, equalTo(store1.name))
    }

    @Test
    fun testDelete() = runBlocking {
        val store1 = Store("Store A","")
        val store2 = Store("Store B","")
        storeDao.insertAll(listOf(store1, store2))

        storeDao.deleteStore(store1.name)

        val storeList = storeDao.getStores().first()
        assertThat(storeList.size, equalTo(1))

        assertThat(storeList[0].name, equalTo(store2.name))
    }
}
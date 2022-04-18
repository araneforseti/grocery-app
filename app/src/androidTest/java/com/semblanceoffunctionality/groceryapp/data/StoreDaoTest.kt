package com.semblanceoffunctionality.groceryapp.data

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.semblanceoffunctionality.groceryapp.utilities.DATABASE_NAME
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

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
        Log.println(Log.DEBUG,"tag","Got here")

        storeDao = database.storeDao()
    }

    @After fun closeDb() {
        database.close()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteDatabase(DATABASE_NAME);
    }

    @Test
    fun testGetAllStores_whenNoStores() = runBlocking {
        val resultList = storeDao.getAll()
        assertThat(resultList.size, equalTo(0))
    }

    @Test
    fun testGetAllStores_whenMultipleStores() = runBlocking {
        val store1 = Store("Store1", "Address1")
        val store2 = Store("Store2", "Address2")
        storeDao.insertAll(listOf(store1, store2))

        val resultList = storeDao.getAll()
        assertThat(resultList.size, equalTo(2))
        assertThat(resultList[0], equalTo(store1))
        assertThat(resultList[1], equalTo(store2))
    }

    @Test
    fun testFindByName() = runBlocking {
        val store1 = Store("Store1", "Address1")
        val store2 = Store("Store2", "Address2")
        storeDao.insertAll(listOf(store1, store2))

        val resultList = storeDao.findByName(store1.name)
        assertThat(resultList, equalTo(store1))
    }

    @Test
    fun testDeleteItem() = runBlocking {
        val store1 = Store("Store1", "Address1")
        val store2 = Store("Store2", "Address2")
        storeDao.insertAll(listOf(store1, store2))

        storeDao.delete(store1)
        val resultList = storeDao.getAll()
        assertThat(resultList.size, equalTo(1))
        assertThat(resultList[0], equalTo(store2))
    }

    @Test
    fun testGetAddress() = runBlocking {
        val store1 = Store("Store1", "Address1")
        storeDao.insertAll(listOf(store1))

        val result = storeDao.getAddress(store1.name)
        assertThat(result, equalTo(store1.address))
    }
}
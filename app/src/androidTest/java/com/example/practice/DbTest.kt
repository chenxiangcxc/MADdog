package com.example.practice

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.practice.repo.AppDatabase
import com.example.practice.repo.DogDao
import com.example.practice.repo.DogEntity
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DbTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: DogDao
    private val dog = DogEntity(1, "chou chou")

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.dogDao()
    }

    @Test
    fun testDb() = runBlocking {
        dao.insertDog(dog)
        val name = dao.getDogName(1)
        Assert.assertEquals(name, "chou chou")
    }

    @After
    fun clearUp() {
        db.close()
    }
}
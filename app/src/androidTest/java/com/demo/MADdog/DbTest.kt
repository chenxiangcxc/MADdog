package com.demo.MADdog

import androidx.room.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.MADdog.repo.AppDatabase
import com.demo.MADdog.repo.DogDao
import com.demo.MADdog.repo.DogEntity
import org.junit.Assert
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
    private val dog = DogEntity("chow", "www.url1.com")

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.dogDao()
    }

    @Test
    fun testDogDb() = runBlocking {
        dao.insertDog(dog)
        val name = dao.getDogNameList()[0]
        Assert.assertEquals(name, "chow")

        var url = dao.getDogImageUrl(name)
        Assert.assertEquals(url, "www.url1.com")

        dog.url = "www.url2.com"
        dao.updateDog(dog)
        url = dao.getDogImageUrl(name)
        Assert.assertEquals(url, "www.url2.com")
    }

    @After
    fun clearUp() {
        db.close()
    }
}
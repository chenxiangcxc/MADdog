package com.demo.MADdog

import androidx.test.platform.app.InstrumentationRegistry
import com.demo.MADdog.repo.ApiResult
import com.demo.MADdog.repo.DogRepo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepoTest {
    private lateinit var repo: DogRepo

    private val dogName = "american"
    private val dogUrl = "https://images.dog.ceo/breeds/terrier-american/n02093428_10164.jpg"

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = DogRepo(context)
    }

    @Test
    fun testRepo() = runBlocking {
        val list = repo.getDogNameList() as ApiResult.Success<List<String>>
        val dogList = list.data

        assertEquals(dogList.size, 23)
        assertEquals(dogList[0], dogName)

        repo.getDogImageUrl(dogList[0]).collectLatest {
            assertEquals(it, dogUrl)
        }
    }
}
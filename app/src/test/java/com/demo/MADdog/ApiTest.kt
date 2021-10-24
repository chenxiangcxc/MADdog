package com.demo.MADdog

import com.demo.MADdog.repo.ApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ApiTest {
    private val dogName = "american"
    private val dogUrl = "https://images.dog.ceo/breeds/terrier-american/n02093428_10164.jpg"

    @Test
    fun testGetDogList() = runBlocking {
        val message = ApiService.getApiService().getDogNameList().message
        assertEquals(message[0], dogName)
        assertEquals(message.size, 23)
    }

    @Test
    fun testGetDogUrl() = runBlocking {
        val url = ApiService.getApiService().getDogImageUrlList(dogName).message[0]
        assertEquals(url, dogUrl)
    }
}
package com.example.practice

import com.example.practice.repo.ApiService
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ApiTest {
    //private val dispacher = TestCoroutineDispatcher()
    private val resp = "Response(message=[afghan, basset, blood, english, ibizan, plott, walker], status=success, code=0)"

    @Test
    fun testRequestDogList() = runBlocking {
        val str = ApiService.getApiService().listBreed().toString()
        assertEquals(str, resp)

        //vm.requestDogListSuspendTest(dispacher, CoroutineScope(dispacher))
        //val str = vm.requestDogListSuspendTest(dispacher, CoroutineScope(dispacher))
        //dispacher.cleanupTestCoroutines()
    }
}
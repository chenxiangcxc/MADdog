package com.example.practice.viewmodel

import androidx.lifecycle.*
import com.example.practice.repo.ApiService
import com.example.practice.repo.AppDatabase
import com.example.practice.repo.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DogViewModel : ViewModel() {
// Suspend fun
    private val _dogList: MutableLiveData<Result<Response<List<String>>>> = MutableLiveData()
    val dogList: LiveData<Result<Response<List<String>>>> = _dogList

    fun requestDogListSuspend() {
        viewModelScope.launch {
            val res = try {
                Result.success(ApiService.getApiService().listBreed())
            } catch (e: Exception) {
                Result.failure(e)
            }

            // 注意 viewModelScope 默认线程是主线程，所以这里直接用setValue()即可
            // 如果使用 viewModelScope.launch(Dispatchers.IO)，这里会崩溃！
            _dogList.value = res
        }
    }

// State Flow
    // StateFlow must have a init value
    private val initState: Result<Response<List<String>>> = Result.success(Response(listOf("Loading"), "", 0))

    private val _dogListFlow: MutableStateFlow<Result<Response<List<String>>>> = MutableStateFlow(initState)
    val dogListFlow: StateFlow<Result<Response<List<String>>>> = _dogListFlow

    fun requestDogListFlow(scope: CoroutineScope = viewModelScope) {
        scope.launch() {
            while (isActive) {
                _dogListFlow.value = initState

                val res = try {
                    Result.success(ApiService.getApiService().listBreed())
                } catch (e: Exception) {
                    Result.failure(e)
                }

                _dogListFlow.value = res

                delay(5000)
            }
        }
    }

// Live Data
    val logListLiveData: LiveData<Result<Response<List<String>>>> = liveData {
        try {
            emit(Result.success(ApiService.getApiService().listBreed()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun requestDogListSuspendTest(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        scope: CoroutineScope = viewModelScope
    ): Result<Response<List<String>>> {
        var r: Result<Response<List<String>>>? = null
        //scope.launch(dispatcher){
            try {
                r = Result.success(ApiService.getApiService().listBreed())
            } catch (e: Exception) {
                r = Result.failure(e)
            }
        //}
        return r!!
    }
}
package com.demo.MADdog.repo

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DogRepo(context : Context) {

    private val apiService = ApiService.getApiService()
    private val appDatabase = AppDatabase.getDbService(context)
    private val dogDao = appDatabase.dogDao()

    suspend fun getDogNameList(): Flow<List<String>> = flow {
        var dogNameList = dogDao.getDogNameList()

        if (dogNameList.isEmpty()) {
            dogNameList =
                try {
                    apiService.getDogNameList().message
                } catch(e: Exception) {
                    Log.e("API error", "Fail to get dog name list!")
                    emptyList()
                }

            dogNameList.forEach {
                dogDao.insertDog(DogEntity(it, ""))
            }
        }

        emit(dogNameList)
    }.flowOn(Dispatchers.IO)

    suspend fun getDogImageUrl(name: String): Flow<String> = flow {
        var dogUrl = dogDao.getDogImageUrl(name)

        if (dogUrl.isEmpty()) {
            dogUrl =
                try {
                    apiService.getDogImageUrlList(name).message[0]
                } catch(e: Exception) {
                    Log.e("API error", "Fail to get dog URL list!")
                    ""
                }

            dogDao.updateDog(DogEntity(name, dogUrl))
        }

        emit(dogUrl)
    }.flowOn(Dispatchers.IO)
}
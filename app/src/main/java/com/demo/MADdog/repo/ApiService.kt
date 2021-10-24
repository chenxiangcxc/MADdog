package com.demo.MADdog.repo

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("breed/terrier/{dog_name}/images")
    suspend fun getDogImageUrlList(@Path("dog_name") dogName: String) : Response<List<String>>

    @GET("breed/terrier/list")
    suspend fun getDogNameList(): Response<List<String>>

    companion object {
        private const val BASE_URL = "https://dog.ceo/api/"
        private var service: ApiService? = null

        @Synchronized
        fun getApiService(): ApiService {
            if (null == service) {
                val httpLoggingInterceptor =
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

                val client = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                service = retrofit.create(ApiService::class.java)
            }

            return service!!
        }
    }
}
package com.example.practice.repo

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    // getWithParamExample(3, "desc") == "group/3/users?sort=decs"
    @GET("group/{id}/users")
    suspend fun getWithParamExample(@Path("id") groupId: Int, @Query("sort") sort: String)

    @POST("user/new")
    suspend fun postWithBodyExample(@Body user: String)

    @GET("breed/hound/list")
    suspend fun listBreed(): Response<List<String>>

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
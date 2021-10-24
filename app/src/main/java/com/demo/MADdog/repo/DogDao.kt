package com.demo.MADdog.repo

import androidx.room.*

@Dao
interface DogDao {
    @Query("SELECT name FROM Dogs")
    suspend fun getDogNameList(): List<String>

    @Query("SELECT url FROM Dogs WHERE name = :name")
    suspend fun getDogImageUrl(name: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: DogEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDog(dog: DogEntity)
}
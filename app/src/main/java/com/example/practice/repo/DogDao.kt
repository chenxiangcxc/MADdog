package com.example.practice.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Query("SELECT * FROM Dogs")
    fun getAll(): Flow<List<DogEntity>>

    @Query("SELECT name FROM Dogs WHERE uid = :id")
    suspend fun getDogName(id: Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dogsList: List<DogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: DogEntity)
}
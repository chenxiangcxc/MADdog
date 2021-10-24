package com.demo.MADdog.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DogEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao

    companion object {
        private var service: AppDatabase? = null

        @Synchronized
        fun getDbService(context: Context): AppDatabase {
            if (service == null) {
                service = Room.databaseBuilder(context, AppDatabase::class.java,"database-dog").build()
            }

            return service!!
        }
    }
}
package com.example.practice.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dogs")
data class DogEntity(
    @PrimaryKey @ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "name") val name: String
)
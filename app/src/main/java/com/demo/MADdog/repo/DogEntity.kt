package com.demo.MADdog.repo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dogs")
data class DogEntity(
    @PrimaryKey val name: String,
    var url: String
)
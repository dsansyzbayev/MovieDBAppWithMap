package com.example.moviedbappwithmap.data.CinemaDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinemas")
class Cinema (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val name : String? = null,
    val address : String? = null,
    val phoneNumber : String? = null,
    val poster : String ? = null,
    val desription : String ? = null
)
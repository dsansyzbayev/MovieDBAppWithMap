package com.example.moviedbappwithmap.data.CinemaDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import retrofit2.http.DELETE

@Dao
interface DaoCinemas {
    @Query("SELECT * FROM cinemas")
    fun getCinemas(): LiveData<List<Cinema>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCinema(cinema: Cinema)

    @Query("DELETE FROM cinemas")
    suspend fun deleteCinemas()

    @Query("SELECT * FROM cinemas WHERE id = :id")
    fun getCinema(id: Int) : LiveData<Cinema>

}
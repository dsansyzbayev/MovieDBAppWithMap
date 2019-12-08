package com.example.moviedbappwithmap.domain.repository

import androidx.lifecycle.LiveData
import com.example.moviedbappwithmap.data.CinemaDataBase.Cinema

interface CinemaRepository{
    fun getCinemas(): LiveData<List<Cinema>>
    fun getCinema(id : Int) : LiveData<Cinema>
}
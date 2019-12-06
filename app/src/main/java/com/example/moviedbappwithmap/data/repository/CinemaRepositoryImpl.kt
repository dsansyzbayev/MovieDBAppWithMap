package com.example.moviedbappwithmap.data.repository

import androidx.lifecycle.LiveData
import com.example.moviedbappwithmap.data.CinemaDataBase.Cinema
import com.example.moviedbappwithmap.data.CinemaDataBase.DaoCinemas
import com.example.moviedbappwithmap.domain.repository.CInemaRepository

class CinemaRepositoryImpl(private val daoCinemas: DaoCinemas) : CInemaRepository {
    override fun getCinemas(): LiveData<List<Cinema>> = daoCinemas.getCinemas()

    override fun getCinema(id: Int): LiveData<Cinema> = daoCinemas.getCinema(id)
}
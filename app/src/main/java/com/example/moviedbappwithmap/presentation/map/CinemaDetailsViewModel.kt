package com.example.moviedbappwithmap.presentation.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedbappwithmap.data.CinemaDataBase.Cinema
import com.example.moviedbappwithmap.data.CinemaDataBase.CinemaDBRoom
import com.example.moviedbappwithmap.data.repository.CinemaRepositoryImpl
import com.example.moviedbappwithmap.domain.repository.CinemaRepository
import kotlinx.coroutines.launch

class CinemaDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: CinemaRepository

    lateinit var liveData: LiveData<Cinema>

    init {
        val daoCinema = CinemaDBRoom.getDatabase(application, viewModelScope).daoCinema()
        repository = CinemaRepositoryImpl(daoCinema)
    }

    fun getCinema(id: Int) {
        viewModelScope.launch {
            val cinema = repository.getCinema(id)
            cinema.let { cinema ->
                liveData = cinema
            }
        }
    }
}
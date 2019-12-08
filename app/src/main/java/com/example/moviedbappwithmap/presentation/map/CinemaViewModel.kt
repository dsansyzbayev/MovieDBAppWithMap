package com.example.moviedbappwithmap.presentation.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedbappwithmap.data.CinemaDataBase.Cinema
import com.example.moviedbappwithmap.data.CinemaDataBase.CinemaDBRoom
import com.example.moviedbappwithmap.data.repository.CinemaRepositoryImpl
import com.example.moviedbappwithmap.domain.repository.CinemaRepository

class CinemaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CinemaRepository

    var liveData: LiveData<List<Cinema>>

    init {
        val daoCinema = CinemaDBRoom.getDatabase(application, viewModelScope).daoCinema()
        repository = CinemaRepositoryImpl(daoCinema)
        liveData = repository.getCinemas()
    }
}
package com.example.moviedbappwithmap.presentation.map

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviedbappwithmap.base.BaseViewModel
import com.example.moviedbappwithmap.data.CinemaDataBase.Cinema
import com.example.moviedbappwithmap.data.CinemaDataBase.CinemaDBRoom
import com.example.moviedbappwithmap.data.CinemaDataBase.DaoCinemas
import com.example.moviedbappwithmap.data.repository.CinemaRepositoryImpl
import com.example.moviedbappwithmap.domain.repository.CinemaRepository
import com.example.moviedbappwithmap.exceptions.NoConnectionException

class CinemaViewModel(daoCinemas: DaoCinemas) : BaseViewModel() {
    private val repository: CinemaRepository

    var liveData: LiveData<List<Cinema>>

    init {
        repository = CinemaRepositoryImpl(daoCinemas)
        liveData = repository.getCinemas()
    }

    override fun handleError(e: Throwable) {
        if(e is NoConnectionException){

        }
    }
}
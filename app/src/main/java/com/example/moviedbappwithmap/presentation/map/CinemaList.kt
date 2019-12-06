package com.example.moviedbappwithmap.presentation.map

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbappwithmap.R
import com.example.moviedbappwithmap.data.CinemaDataBase.Cinema
import com.example.moviedbappwithmap.data.CinemaDataBase.CinemaDBRoom
import com.example.moviedbappwithmap.data.repository.CinemaRepositoryImpl
import com.example.moviedbappwithmap.domain.repository.CInemaRepository
import kotlinx.android.synthetic.main.fragment_cinemalist.*

class CinemaList : Fragment(){
    private lateinit var navController: NavController
    private lateinit var listViewModel: CinemaViewModel
    private lateinit var cinemaRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cinemalist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(CinemaViewModel::class.java)
        bindViews()
        recyclerViewCinemass = cinemaListAdapter
    }

    private val cinemaListAdapter by lazy {
        CinemaListAdapter (context = context, itemClickListener = onClickListener)
    }

    fun bindViews(view: View) = with(view){
        navController = Navigation.findNavController(this)
        cinemaRecyclerView = findViewById(R.id.recyclerViewCinemass)
        cinemaRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    class CinemaViewModel(application: Application):AndroidViewModel(application){
        private val repository: CInemaRepository = CinemaRepositoryImpl(daoCinemas =
        CinemaDBRoom.getDatabase(application, viewModelScope).daoCinema())
        val liveData : LiveData<List<Cinema>> = repository.getCinemas()
    }
}
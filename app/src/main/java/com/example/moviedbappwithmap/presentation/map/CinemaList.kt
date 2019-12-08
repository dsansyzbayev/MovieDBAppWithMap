package com.example.moviedbappwithmap.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbappwithmap.R
import com.example.moviedbappwithmap.base.BaseFragment
import com.example.moviedbappwithmap.data.CinemaDataBase.Cinema
import com.example.moviedbappwithmap.presentation.map.adapters.CinemaListAdapter

class CinemaList : BaseFragment() {

    private lateinit var navController: NavController
    private lateinit var listViewModel: CinemaViewModel
    private lateinit var recyclerViewCinema: RecyclerView

    companion object {
        fun newInstance() : CinemaList = CinemaList()
    }

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
        bindViews(view)
        setAdapter()
        setData()
    }

    fun setData() {
        listViewModel.liveData.observe(viewLifecycleOwner, Observer { cinemaList ->
            cinemaListAdapter.setCinemas(cinemaList)
        })
    }

    fun setAdapter() {
        recyclerViewCinema.adapter = cinemaListAdapter
    }
    private val onClickListener = object : CinemaListAdapter.ItemClickListener {
        override fun onItemClick(item: Cinema) {
            val bundle = Bundle()
            item.id?.let { id->
                bundle.putInt("cinema_id", id)
            }
            navController.navigate(R.id.action_cinemaFragment_to_cinemaDetailsFragment, bundle)
        }
    }

    private val cinemaListAdapter by lazy {
        CinemaListAdapter (
            context = context,
            itemClickListener = onClickListener
        )
    }

    override fun bindViews(view: View) = with(view){
        navController = Navigation.findNavController(this)
        recyclerViewCinema = findViewById(R.id.recyclerViewCinemass)
        recyclerViewCinema.layoutManager = LinearLayoutManager(context)
    }
}
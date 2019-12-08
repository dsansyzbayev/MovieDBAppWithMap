package com.example.moviedbappwithmap.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.moviedbappwithmap.R
import com.example.moviedbappwithmap.base.BaseFragment

class CinemaDetailsFragment : BaseFragment() {
    private lateinit var viewModel: CinemaDetailsViewModel
    private lateinit var cinema_name : TextView
    private lateinit var cinema_pic : ImageView
    private lateinit var cinema_address : TextView
    private lateinit var cinema_phoneNumber : TextView
    private lateinit var cinema_details : TextView
    private var cinemaId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cinema_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CinemaDetailsViewModel::class.java)
        bindViews(view)
        setData()
    }

    override fun bindViews(view: View) {
        cinema_name = view.findViewById(R.id.cinema_name)
        cinema_address = view.findViewById(R.id.cinema_address)
        cinema_phoneNumber = view.findViewById(R.id.cinema_phoneNumber)
        cinema_details = view.findViewById(R.id.cinema_details)
        cinema_pic = view.findViewById(R.id.cinema_pic)
    }

    fun setData() {
        cinemaId = arguments?.getInt("cinema_id")
        cinemaId?.let {it ->
            viewModel.getCinema(it)
        }

        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            val imageUrl = result.poster
            Glide.with(this)
                .load(imageUrl)
                .into(cinema_pic)
            cinema_name.text = result.name
            cinema_address.text = result.address
            cinema_phoneNumber.text = result.phoneNumber
            cinema_details.text = result.desription
        })
    }

}
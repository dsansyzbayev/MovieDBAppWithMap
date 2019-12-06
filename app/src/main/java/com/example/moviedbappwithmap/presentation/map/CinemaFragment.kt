package com.example.moviedbappwithmap.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.moviedbappwithmap.R
import com.example.moviedbappwithmap.presentation.map.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class CinemaFragment : Fragment(){
    private lateinit var viewPager: ViewPager
    private lateinit var tablayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cinemas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tablayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
        val vpAdapter = childFragmentManager.let {fragmentManager ->
            ViewPagerAdapter(
                fragmentManager
            )
        }
        val cinemaList : CinemaList = CinemaList()
        val mapFragment : MapFragment = MapFragment()

        vpAdapter.addFragment(cinemaList, "Cinemas")
        vpAdapter.addFragment(mapFragment, "Map")

        viewPager.adapter = vpAdapter
        tablayout.setupWithViewPager(viewPager)
    }
}
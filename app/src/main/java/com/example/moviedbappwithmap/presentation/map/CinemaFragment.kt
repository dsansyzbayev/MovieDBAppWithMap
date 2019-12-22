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

class CinemaFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cinemas,container, false )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setViewPager()
    }

    private fun setViews(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
    }

    private fun setViewPager() {
        val adapter = childFragmentManager.let { fragmentManager ->
            ViewPagerAdapter(fragmentManager)
        }
        val cinemaListFragment: CinemaList = CinemaList.newInstance()
        val cinemaMapFragment: MapFragment = MapFragment.newInstance()

        adapter.addFragment(cinemaListFragment, "Cinemas")
        adapter.addFragment(cinemaMapFragment, "Map")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}
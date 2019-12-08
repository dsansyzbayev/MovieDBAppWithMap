package com.example.moviedbappwithmap.presentation.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.moviedbappwithmap.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap
    private lateinit var navController: NavController
    private var markers = ArrayList<LatLng>()

    companion object {
        fun newInstance() : MapFragment =
            MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setMarkers()
    }

    fun bindViews(view: View) = with(view) {
        navController = Navigation.findNavController(this)
        mapFragment = childFragmentManager.findFragmentById(R.id.cinemaFragment) as SupportMapFragment
    }

    fun setMarkers() {
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            val location1 = LatLng(43.240248,76.9061647)
            googleMap.addMarker(MarkerOptions().position(location1).title("Bekmambetov Cinema"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,10f))

            val location2 = LatLng(43.262044,76.941684)
            googleMap.addMarker(MarkerOptions().position(location2).title("Lumiera Cinema"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location2,10f))

            val location3 = LatLng(43.232963,76.955780)
            googleMap.addMarker(MarkerOptions().position(location3).title("CINEMAX Dostyk Multiplex"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location3,10f))

            val location4 = LatLng(43.225303,76.907712)
            googleMap.addMarker(MarkerOptions().position(location4).title("Kinopark 5 Atakent"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location4,10f))

            val location5 = LatLng(43.218182,76.927779)
            googleMap.addMarker(MarkerOptions().position(location5).title("Kinopark 11 Esentai"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location5,10f))

            val location6 = LatLng(43.234153,76.935827)
            googleMap.addMarker(MarkerOptions().position(location6).title("Kinopark 16 Forum"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location6,10f))

            val location7 = LatLng(43.226818,76.840168)
            googleMap.addMarker(MarkerOptions().position(location7).title("Kinopark 8 Moskva"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location7,10f))

            val location8 = LatLng(43.211946,76.842262)
            googleMap.addMarker(MarkerOptions().position(location7).title("Kinopark 6 Sputnik"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location8,10f))
        })
    }
}

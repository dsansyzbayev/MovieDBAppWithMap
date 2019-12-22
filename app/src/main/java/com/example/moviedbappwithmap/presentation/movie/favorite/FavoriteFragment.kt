package com.example.moviedbappwithmap.presentation.movie.favorite


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.moviedbappwithmap.R
import com.example.moviedbappwithmap.base.BaseFragment
import com.example.moviedbappwithmap.data.models.MovieData
import com.example.moviedbappwithmap.presentation.movie.list.MovieAdapter
import com.example.moviedbappwithmap.utils.AppConstants
import com.example.moviedbappwithmap.utils.AppPreferences
import com.example.moviedbappwithmap.utils.PaginationListener
import org.koin.android.ext.android.inject

class FavoriteFragment : BaseFragment() {
    private lateinit var navController: NavController
    private val viewModel: FavoriteMoviesViewModel by inject()
    private lateinit var rvFavMovies: RecyclerView
    private lateinit var srlFavMovies: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar


    private var currentPage = PaginationListener.PAGE_START
    private var isLastPage = false
    private var isLoading = false
    private var itemCount = 0

    private var accountId: Int? = null
    private var sessionId: String? = null


    private val onClickListener = object: MovieAdapter.ItemClickListener {
        override fun onItemClick(item: MovieData) {
            Log.d("movieDb: movieID", item.id.toString())
            navController.navigate(
                R.id.action_favoriteFragment_to_movieDetailsFragment,
                bundleOf(
                    AppConstants.MOVIE_ID to item.id
                )
            )
        }
    }


    private val moviesAdapter by lazy {
        MovieAdapter(
            itemClickListener = onClickListener
        )
    }

    private fun setAdapter() {
        rvFavMovies.adapter = moviesAdapter
    }

    private fun initId() {
        accountId = AppPreferences.getAccountId(activity?.applicationContext!!)
        sessionId = AppPreferences.getSessionId(activity?.applicationContext!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initId()
        bindViews(view)
        setAdapter()
        setData()
    }

    override fun bindViews(view: View) = with(view) {
        navController = Navigation.findNavController(this)
        progressBar = view.findViewById(R.id.progressBar)
        srlFavMovies = view.findViewById(R.id.srlFavMovies)
        rvFavMovies = view.findViewById(R.id.rvFavMovies)
        val layoutManager = LinearLayoutManager(context)
        rvFavMovies.layoutManager = layoutManager
        rvFavMovies.addOnScrollListener(object: PaginationListener(layoutManager) {

            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                viewModel.loadFavMovies(accountId, sessionId, page = currentPage)
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading
        })
        srlFavMovies.setOnRefreshListener {
            moviesAdapter.clearAll()
            itemCount = 0
            currentPage = PaginationListener.PAGE_START
            isLastPage = false
            viewModel.loadFavMovies(accountId, sessionId, page = currentPage)
        }
    }


    fun setData() {
        viewModel.loadFavMovies(accountId, sessionId)

        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is FavoriteMoviesViewModel.State.ShowLoading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is FavoriteMoviesViewModel.State.HideLoading -> {
                    progressBar.visibility = View.GONE
                }
                is FavoriteMoviesViewModel.State.Result -> {
                    itemCount = result.list.size
                    if (currentPage != PaginationListener.PAGE_START) {
                        moviesAdapter.removeLoading()
                    }
                    moviesAdapter.addItems(result.list)
                    if (currentPage < result.totalPages) {
                        moviesAdapter.addLoading()
                    } else {
                        isLastPage = true
                    }
                    isLoading = false
                }
                is FavoriteMoviesViewModel.State.Error -> {
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
                is FavoriteMoviesViewModel.State.IntError -> {
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
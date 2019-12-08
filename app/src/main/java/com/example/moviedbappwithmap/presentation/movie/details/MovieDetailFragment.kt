package com.example.moviedbappwithmap.presentation.movie.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.example.moviedbappwithmap.R
import com.example.moviedbappwithmap.base.BaseFragment
import com.example.moviedbappwithmap.utils.AppConstants
import com.example.moviedbappwithmap.utils.AppPreferences

class MovieDetailFragment : BaseFragment() {

    private lateinit var viewModel: MovieDetailViewModel

    private lateinit var progressBar: ProgressBar
    private lateinit var ivPoster: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvOverview: TextView
    private lateinit var tvDetails: TextView
    private lateinit var btnFavorite: ImageButton

    private var movieId: Int? = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        bindViews(view)
        setData()

        val accountId = AppPreferences.getAccountId(activity?.applicationContext!!)
        val sessionId = AppPreferences.getSessionId(activity?.applicationContext!!)

        btnFavorite.setOnClickListener{
            movieId?.let {
                accountId?.let {
                    sessionId?.let {
                        viewModel.setFavorite(accountId, movieId!!, sessionId)
                    }
                }
            }
        }
    }

    override fun bindViews(view: View) = with(view){
        progressBar = view.findViewById(R.id.progressBar)
        ivPoster = view.findViewById(R.id.tvPoster)
        tvName = view.findViewById(R.id.tvTitle)
        tvRating = view.findViewById(R.id.tvRating)
        tvGenre = view.findViewById(R.id.tvGenre)
        tvOverview = view.findViewById(R.id.tvOverview)
        tvDetails = view.findViewById(R.id.tvDetails)
        btnFavorite = view.findViewById(R.id.btnFavorite)

        movieId = arguments?.getInt(AppConstants.MOVIE_ID)
    }
    fun setData() {
        movieId?.let { movieId ->
            viewModel.getMovie(movieId)
        }

        viewModel.liveData.observe(viewLifecycleOwner, Observer {result ->
            when(result) {
                is MovieDetailViewModel.State.ShowLoading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is MovieDetailViewModel.State.HideLoading -> {
                    progressBar.visibility = View.GONE
                }
                is MovieDetailViewModel.State.Result -> {
                    val imageUrl = "${AppConstants.POSTER_BASE_URL}${result.movie.posterPath}"
                    Glide.with(this)
                        .load(imageUrl)
                        .into(ivPoster)
                    tvName.text = result.movie.title
                    tvRating.text = "Rating: ${result.movie.voteAverage}/10"
                    tvGenre.text = "Genre: ${result.movie.genres?.first()?.name}"
                    tvOverview.text = result.movie.overview
                    tvDetails.text = "Release Date: ${result.movie.releaseDate}" +
                            "\nRevenue: $${result.movie.revenue}" +
                            "\nBudget: ${result.movie.budget}" +
                            "\nHomepage: ${result.movie.homepage}" +
                            "\nOriginal Title: ${result.movie.original_title}" +
                            "\nOriginal Language: ${result.movie.origiinal_language}" +
                            "\nTagLine: ${result.movie.tagline}"
                }
                is MovieDetailViewModel.State.FavoriteMovie -> {
                    if (result.resultCode == 1) {
                        Toast.makeText(context, "Successfully added to your favorite movies!", Toast.LENGTH_SHORT).show()
                    } else if (result.resultCode == 12) {
                        Toast.makeText(context, "Movie was updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
                is MovieDetailViewModel.State.Error -> {
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
                is MovieDetailViewModel.State.IntError -> {
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
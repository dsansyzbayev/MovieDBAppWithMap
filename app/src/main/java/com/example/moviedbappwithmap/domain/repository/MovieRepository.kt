package com.example.moviedbappwithmap.domain.repository

import com.example.moviedbappwithmap.data.models.MovieData
import com.example.moviedbappwithmap.data.models.MovieResponseData

interface MovieRepository {

    // Movie
    suspend fun getPopularMovies(page: Int) : MovieResponseData?

    suspend fun getMovieById(movieId: Int): MovieData?

    suspend fun getFavoriteMovies(accountId: Int, sessionId: String, page: Int): MovieResponseData?

    suspend fun rateMovie(movieId: Int, accountId: Int, sessionId: String): Int?


}
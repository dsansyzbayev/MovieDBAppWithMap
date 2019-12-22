package com.example.moviedbappwithmap.data.repository

import com.example.moviedbappwithmap.data.models.MovieData
import com.example.moviedbappwithmap.data.models.MovieResponseData
import com.example.moviedbappwithmap.data.network.ApiClient
import com.example.moviedbappwithmap.data.network.MovieApi
import com.example.moviedbappwithmap.domain.repository.MovieRepository
import com.google.gson.JsonObject

class MovieRepositoryImpl( private val movieApi: MovieApi): MovieRepository {

    //Movie
    override suspend fun getPopularMovies(page: Int) =
        movieApi.getPopularMovies(page).await().body()

    override suspend fun getFavoriteMovies(
        accountId: Int,
        sessionId: String,
        page: Int
    ): MovieResponseData? =
        movieApi.getFavoriteMovies(accountId, sessionId, page).await().body()

    override suspend fun getMovieById(movieId: Int): MovieData? =
        movieApi.getMovie(movieId).await().body()

    override suspend fun rateMovie(movieId: Int, accountId: Int, sessionId: String): Int? {
        val body = JsonObject().apply {
            addProperty("media_type", "movie")
            addProperty("media_id", movieId)
            addProperty("favorite", true)
        }
        val response = movieApi.rateMovie(accountId, sessionId, body).await()
        return response.body()?.getAsJsonPrimitive("status_code")?.asInt
    }
}
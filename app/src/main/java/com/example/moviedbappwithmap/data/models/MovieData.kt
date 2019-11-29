package com.example.moviedbappwithmap.data.models

import com.google.gson.annotations.SerializedName

data class MovieData (
    @SerializedName("id") val id: Int? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("budget") val budget: Double? = null,
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("original_title") val original_title: String? = null,
    @SerializedName("original_language") val origiinal_language: String? = null,
    @SerializedName("revenue") val revenue: Integer? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("genres") val genres: List<Genre>? = null
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
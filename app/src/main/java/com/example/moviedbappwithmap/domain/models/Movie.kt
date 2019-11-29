package com.example.moviedbappwithmap.domain.models

data class Movie (
    val id: Int?,
    val voteCount: Int?,
    val adult: Boolean?,
    val title: String?,
    val voteAverage: Double?,
    val overview: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val backdropPath: String?,
    var budget: Double?,
    var homepage: String?,
    var original_title: String?,
    var original_language: String?,
    var revenue: Integer?,
    var tagline: String?
)
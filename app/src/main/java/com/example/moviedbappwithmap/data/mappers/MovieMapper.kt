package com.example.moviedbappwithmap.data.mappers

import com.example.moviedbappwithmap.data.models.MovieData
import com.example.moviedbappwithmap.domain.Mapper
import com.example.moviedbappwithmap.domain.models.Movie

class MovieMapper: Mapper<MovieData, Movie> {

    override fun from(item: MovieData): Movie {
        return Movie (
            id = item.id,
            title = item.title,
            voteCount = item.voteCount,
            adult = item.adult,
            voteAverage = item.voteAverage,
            overview = item.overview,
            releaseDate = item.releaseDate,
            posterPath = item.posterPath,
            backdropPath = item.backdropPath,
            budget = item.budget,
            original_language = item.origiinal_language,
            original_title = item.original_title,
            homepage = item.homepage,
            revenue = item.revenue,
            tagline = item.tagline
        )
    }

    override fun to(item: Movie): MovieData {
        return MovieData(
            id = item.id,
            title = item.title,
            voteCount = item.voteCount,
            adult = item.adult,
            voteAverage = item.voteAverage,
            overview = item.overview,
            releaseDate = item.releaseDate,
            posterPath = item.posterPath,
            backdropPath = item.backdropPath,
            budget = item.budget,
            original_title = item.original_title,
            origiinal_language = item.original_language,
            homepage = item.homepage,
            revenue = item.revenue,
            tagline = item.tagline
        )
    }
}
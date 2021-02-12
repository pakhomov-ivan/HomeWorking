package ru.navifromnorth.homeworking.repository.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.navifromnorth.homeworking.repository.api.dto.GenresListDTO
import ru.navifromnorth.homeworking.repository.api.dto.MovieDetailsDTO
import ru.navifromnorth.homeworking.repository.api.dto.MoviesListDTO
import ru.navifromnorth.homeworking.repository.api.dto.VideosListDTO

interface TMDBServiceAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): MoviesListDTO

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String
    ): MovieDetailsDTO

    @GET("movie/{movie_id}/videos")
    suspend fun getViedos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String
    ): VideosListDTO

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") api_key: String
    ): GenresListDTO
}
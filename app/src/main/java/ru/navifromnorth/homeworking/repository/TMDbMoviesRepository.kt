package ru.navifromnorth.homeworking.repository

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.navifromnorth.homeworking.repository.api.TMDBServiceAPI

object TMDbMoviesRepository {

    val moviesRepository by lazy {
        createMoviesRepository()
    }

    private fun createMoviesRepository(): MoviesRepository {
        return MoviesRepository(createTMDbServiceApi(), createTMDbServiceMapper())
    }

    private fun createTMDbServiceApi(): TMDBServiceAPI {
        return createRetrofit().create()
    }

    private fun createTMDbServiceMapper(): TMDBServiceMapper {
        return TMDBServiceMapper()
    }

    private fun createRetrofit() = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}
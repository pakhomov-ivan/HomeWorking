package ru.navifromnorth.homeworking.repository.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.net.InetSocketAddress
import java.net.Proxy

object TMDbMoviesRepositoryProvider {

    val TMDbMoviesRepository by lazy {
        createMoviesRepository()
    }

    private fun createMoviesRepository(): TMDbMoviesRepository {
        return TMDbMoviesRepository(createTMDbServiceApi(), createTMDbServiceMapper())
    }

    private fun createTMDbServiceApi(): TMDBServiceAPI {
        return createRetrofit().create()
    }

    private fun createTMDbServiceMapper(): TMDBServiceMapper {
        return TMDBServiceMapper()
    }

    private fun createRetrofit() = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("51.79.79.111", 3128))).build()
        )
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}
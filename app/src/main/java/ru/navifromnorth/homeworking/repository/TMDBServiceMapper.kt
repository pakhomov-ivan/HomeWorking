package ru.navifromnorth.homeworking.repository

import ru.navifromnorth.homeworking.data.Genre
import ru.navifromnorth.homeworking.data.Movie
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.data.Video
import ru.navifromnorth.homeworking.repository.api.dto.*

class TMDBServiceMapper {

    //Movie
    fun map(moviesListDTO: MoviesListDTO, genresList: List<Genre>): List<Movie> =
        moviesListDTO.results.map { movieDTO -> map(movieDTO, genresList) }

    fun map(movieDTO: MovieDTO, genresList: List<Genre>) = Movie(
        id = movieDTO.id,
        title = movieDTO.title,
        overview = movieDTO.title,
        genres = genresList.filter { movieDTO.genres.contains(it.id) },
        minimumAge = if (movieDTO.isAdult) 18 else 0,
        numberOfRatings = movieDTO.countReviews,
        poster = POSTER_BASE_URL + movieDTO.posterUrl,
        ratings = movieDTO.rating
    )

    //Genre
    fun map(genresListDTO: GenresListDTO): List<Genre> =
        genresListDTO.genres.map { genreDTO -> map(genreDTO) }

    fun map(genreDTO: GenreDTO): Genre = Genre(id = genreDTO.id, name = genreDTO.name)

    //MovieDetails
    fun map(movieDetails: MovieDetailsDTO, videoList: VideosListDTO?) = MovieDetails(
        overview = movieDetails.overview,
        id = movieDetails.id,
        adult = movieDetails.adult,
        runtime = movieDetails.runtime,
        backdrop = BACKDROP_BASE_URL + movieDetails.backdrop,
        title = movieDetails.title,
        vote_average = movieDetails.vote_average,
        vote_count = movieDetails.vote_count,
        genres = movieDetails.genres.map { genreDTO -> map(genreDTO) } ,
        video = videoList?.let { map(it) }
    )

    //Video
    fun map(videoList: VideosListDTO) = videoList.results.map { videoDTO -> map(videoDTO) }

    fun map(video: VideoDTO) = Video(
        id = video.id,
        name = video.name,
        key = if (video.site == "YouTube") YOUTUBE_BASE_URL + video.key else video.key,
        type = video.type,
        site = video.site
    )

    companion object {
        private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        private const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
        private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
    }
}
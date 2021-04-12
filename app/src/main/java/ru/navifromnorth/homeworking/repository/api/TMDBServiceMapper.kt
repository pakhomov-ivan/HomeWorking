package ru.navifromnorth.homeworking.repository.api

import ru.navifromnorth.homeworking.data.Genre
import ru.navifromnorth.homeworking.data.MovieDetails
import ru.navifromnorth.homeworking.data.Video
import ru.navifromnorth.homeworking.repository.api.dto.*
import ru.navifromnorth.homeworking.repository.database.entities.GenresEntity
import ru.navifromnorth.homeworking.repository.database.entities.MoviesEntity
import ru.navifromnorth.homeworking.repository.database.entities.VideosEntity
import ru.navifromnorth.homeworking.repository.database.entities.relations_entity.MovieWithGenres

object TMDBServiceMapper {

    private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    private const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
    private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="

    //Movie
    fun mapEntity(moviesListDTO: MoviesListDTO): List<MovieWithGenres> =
        moviesListDTO.results.map { movieDTO -> mapEntity(movieDTO, moviesListDTO.page) }

    fun mapEntity(movieDTO: MovieDTO, pageNum: Long) = MovieWithGenres(
        movie = MoviesEntity(
            id = movieDTO.id,
            adult = if (movieDTO.isAdult) 18 else 0,
            vote_count = movieDTO.countReviews,
            vote_average = movieDTO.rating,
            title = movieDTO.title,
            backdrop = BACKDROP_BASE_URL + movieDTO.backdropUrl,
            poster = POSTER_BASE_URL + movieDTO.posterUrl,
            overview = movieDTO.overview,
            hasLike = false,
            page_num = pageNum
        ),
        genres = movieDTO.genres.map { GenresEntity(it) }
    )

    //Genre
    fun map(genresListDTO: GenresListDTO): List<Genre> =
        genresListDTO.genres.map { genreDTO -> map(genreDTO) }

    fun map(genreDTO: GenreDTO): Genre = Genre(id = genreDTO.id, name = genreDTO.name)

    //MovieDetails
//    fun map(movieDetails: MovieDetailsDTO) = MovieDetails(
//        overview = movieDetails.overview,
//        id = movieDetails.id,
//        adult = movieDetails.adult,
//        runtime = movieDetails.runtime,
//        backdrop = BACKDROP_BASE_URL + movieDetails.backdrop,
//        title = movieDetails.title,
//        vote_average = movieDetails.vote_average,
//        vote_count = movieDetails.vote_count,
//        genres = movieDetails.genres.map { genreDTO -> map(genreDTO) }
//    )

    //Video
    //support youtube only
    //unknown how to map other sites
    fun map(videoList: VideosListDTO) =
        videoList.results.map { videoDTO -> map(videoList.id, videoDTO) }.filter { it.site == "YouTube" }

    fun map(movieID: Long, video: VideoDTO) = VideosEntity(
        id = video.id,
        movieID = movieID,
        name = video.name,
        key = YOUTUBE_BASE_URL + video.key,
        type = video.type,
        site = video.site
    )
}
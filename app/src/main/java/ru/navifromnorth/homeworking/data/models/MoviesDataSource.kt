package ru.navifromnorth.homeworking.data.models

import ru.navifromnorth.homeworking.R

class MoviesDataSource() {
    companion object {
        fun getPreviewMovies(): List<Movie> {
            return listOf(
                Movie(
                    titleId = R.string.avengers_end_game,
                    previewImageId = R.drawable.avengers_end_game_preview_poster,
                    PG = 13,
                    hasLike = false,
                    countReviews = 125,
                    rating = 4.0,
                    runtimeInMinutes = 137,
                    tags = setOf(R.string.tag_action, R.string.tag_adventure, R.string.tag_drama)
                ),
                Movie(
                    titleId = R.string.tenet,
                    previewImageId = R.drawable.tenet_preview_poster,
                    PG = 16,
                    hasLike = true,
                    countReviews = 98,
                    rating = 5.0,
                    runtimeInMinutes = 97,
                    tags = setOf(R.string.tag_action, R.string.tag_sci_fi, R.string.tag_thriller)
                ),
                Movie(
                    titleId = R.string.black_widow,
                    previewImageId = R.drawable.black_widow_preview_poster,
                    PG = 13,
                    hasLike = false,
                    countReviews = 38,
                    rating = 4.0,
                    runtimeInMinutes = 102,
                    tags = setOf(R.string.tag_action, R.string.tag_adventure, R.string.tag_sci_fi)
                ),
                Movie(
                    titleId = R.string.wonder_women_1984,
                    previewImageId = R.drawable.wonder_woman_1984_prewiew_poster,
                    PG = 13,
                    hasLike = false,
                    countReviews = 74,
                    rating = 5.0,
                    runtimeInMinutes = 120,
                    tags = setOf(R.string.tag_action, R.string.tag_adventure, R.string.tag_fantasy)
                ),
            )
        }
    }
}
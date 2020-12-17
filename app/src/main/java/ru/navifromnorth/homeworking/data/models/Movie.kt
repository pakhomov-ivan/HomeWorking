package ru.navifromnorth.homeworking.data.models

import ru.navifromnorth.homeworking.R

data class Movie(
    val titleId: Int,
    val previewImageId: Int,
    var hasLike: Boolean,
    val PG: Int,
    val tags: Set<Int>,
    val rating: Double,
    val countReviews: Int,
    val runtimeInMinutes: Int,
) {
    var storylineId: Int? = null//_storylineId
    var cast: Set<Actor>? = null//_cast
    var backgroundPosterId: Int? = null//_backgroundPosterId


    fun initializeDetails() {
        when (titleId) {
            R.string.avengers_end_game -> {
                storylineId = R.string.avengers_end_game_storyline
                backgroundPosterId = R.drawable.avengers_end_game_background_poster
                cast = setOf(
                    Actor(
                        nameId = R.string.robert_downey_jr,
                        avatarId = R.drawable.actor_robert_downey_jr
                    ),
                    Actor(
                        nameId = R.string.chris_evans,
                        avatarId = R.drawable.actor_chris_evans,
                    ),
                    Actor(
                        nameId = R.string.mark_ruffalo,
                        avatarId = R.drawable.actor_mark_ruffalo
                    ),
                    Actor(
                        nameId = R.string.chris_hemsworth,
                        avatarId = R.drawable.actor_chris_hemsworth
                    )
                )
            }

            R.string.tenet -> {
                storylineId = R.string.tenet_storyline
                backgroundPosterId = R.drawable.tenet_background_poster
                cast = setOf(
                    Actor(
                        nameId = R.string.john_david_washington,
                        avatarId = R.drawable.actor_john_david_washington
                    ),
                    Actor(
                        nameId = R.string.robert_pattinson,
                        avatarId = R.drawable.actor_robert_pattinson
                    )
                )
            }

            R.string.black_widow -> {
                storylineId = R.string.black_widow_storyline
                backgroundPosterId = R.drawable.black_widow_background_poster
                cast = setOf(
                    Actor(
                        nameId = R.string.scarlett_johansson,
                        avatarId = R.drawable.actor_scarlett_johansson
                    )
                )
            }

            R.string.wonder_women_1984 -> {
                storylineId = R.string.wonder_women_1984_storyline
                backgroundPosterId = R.drawable.wonder_women_1984_background_poster
                cast = setOf(
                    Actor(
                        nameId = R.string.gal_gadot,
                        avatarId = R.drawable.actor_gal_gadot
                    )
                )
            }
        }
    }
}
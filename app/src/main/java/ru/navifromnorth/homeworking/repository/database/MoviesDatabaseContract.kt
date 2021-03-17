package ru.navifromnorth.homeworking.repository.database

import android.provider.BaseColumns

object MoviesDatabaseContract {
    const val DATABASE_NAME = "Movies.db"
    const val DATABASE_VERSION = 3

    object Movies {
        const val TABLE_NAME = "Movies"

        object Columns {
            const val ID = BaseColumns._ID
            const val ADULT = "adult"
            const val BACKDROP = "backdrop"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val POSTER = "poster"
            const val VOTE_AVERAGE = "vote_average"
            const val VOTE_COUNT = "vote_count"
            const val LIKE = "hasLike"
            const val PAGE_NUM = "pageNum"
        }
    }

    object Genres {
        const val TABLE_NAME = "Genres"

        object Columns {
            const val ID = BaseColumns._ID
            const val NAME = "name"
        }
    }

    object Videos {
        const val TABLE_NAME = "Videos"

        object Columns {
            const val ID = BaseColumns._ID
            const val MOVIE_ID = "MovieID"
            const val KEY = "key"
            const val NAME = "name"
            const val SITE = "site"
            const val TYPE = "type"
        }
    }

    //TODO: Добавить актёров
}
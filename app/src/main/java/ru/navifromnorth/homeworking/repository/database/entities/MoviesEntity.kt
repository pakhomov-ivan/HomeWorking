package ru.navifromnorth.homeworking.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.navifromnorth.homeworking.repository.database.MoviesDatabaseContract

@Entity(
        tableName = MoviesDatabaseContract.Movies.TABLE_NAME,
        indices = [Index(MoviesDatabaseContract.Movies.Columns.PAGE_NUM)]
)
data class MoviesEntity(
        @PrimaryKey
        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.ID)
        val id: Long,

        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.ADULT)
        val adult: Int,

        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.BACKDROP)
        val backdrop: String?,

        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.OVERVIEW)
        val overview: String = "",

        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.POSTER)
        val poster: String?,

//        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.RUNTIME)
//        val runtime: Int? = null,

        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.TITLE)
        val title: String,

        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.VOTE_AVERAGE)
        val vote_average: Float,

        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.VOTE_COUNT)
        val vote_count: Int,

        //Service Information
        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.LIKE)
        val hasLike: Boolean,

        @ColumnInfo(name = MoviesDatabaseContract.Movies.Columns.PAGE_NUM)
        val page_num: Long
)
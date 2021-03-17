package ru.navifromnorth.homeworking.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.navifromnorth.homeworking.data.Video
import ru.navifromnorth.homeworking.repository.database.MoviesDatabaseContract

@Entity(tableName = MoviesDatabaseContract.Videos.TABLE_NAME)
data class VideosEntity(
    @PrimaryKey
    @ColumnInfo(name = MoviesDatabaseContract.Videos.Columns.ID)
    val id: String,

    @ColumnInfo(name = MoviesDatabaseContract.Videos.Columns.MOVIE_ID)
    val movieID: Long,

    @ColumnInfo(name = MoviesDatabaseContract.Videos.Columns.NAME)
    val name: String,

    @ColumnInfo(name = MoviesDatabaseContract.Videos.Columns.KEY)
    val key: String,

    @ColumnInfo(name = MoviesDatabaseContract.Videos.Columns.SITE)
    val site: String,

    @ColumnInfo(name = MoviesDatabaseContract.Videos.Columns.TYPE)
    val type: String
) {
    fun asDomain() = Video(
        id = id,
        key = key,
        name = name,
        type = type,
        site = site
    )
}

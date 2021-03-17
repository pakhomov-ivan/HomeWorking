package ru.navifromnorth.homeworking.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.navifromnorth.homeworking.repository.database.entities.VideosEntity

@Dao
interface VideosDao {

    @Insert(entity = VideosEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(video: VideosEntity)

    @Insert(entity = VideosEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllVideos(videosList: List<VideosEntity>)

    @Query("Delete from Videos where _id = :movieID")
    fun dropMovieVideos(movieID: Long)

    @Query("Delete from Videos")
    fun dropAllMovieVideos()
}
package ru.navifromnorth.homeworking.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.navifromnorth.homeworking.repository.database.dao.GenresDao
import ru.navifromnorth.homeworking.repository.database.dao.MoviesDao
import ru.navifromnorth.homeworking.repository.database.dao.VideosDao
import ru.navifromnorth.homeworking.repository.database.entities.GenresEntity
import ru.navifromnorth.homeworking.repository.database.entities.MoviesEntity
import ru.navifromnorth.homeworking.repository.database.entities.VideosEntity
import ru.navifromnorth.homeworking.repository.database.entities.relations_entity.MoviesGenresCrossRefEntity
import ru.navifromnorth.homeworking.utils.SingletonHolder

@Database(
    entities = [MoviesEntity::class, GenresEntity::class, MoviesGenresCrossRefEntity::class, VideosEntity::class],
    version = MoviesDatabaseContract.DATABASE_VERSION
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val movies: MoviesDao
    abstract val genres: GenresDao
    abstract val videos: VideosDao

    companion object : SingletonHolder<MoviesDatabase, Context>({ context: Context ->
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            MoviesDatabaseContract.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    })
}
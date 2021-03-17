package ru.navifromnorth.homeworking.repository.database.dao

import androidx.room.*
import ru.navifromnorth.homeworking.repository.database.entities.GenresEntity

@Dao
interface GenresDao {

    @Query("Select * from Genres")
    fun getAll(): List<GenresEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genresList: List<GenresEntity>)

//    @Query("Delete from Genres")
//    fun deleteAll()

//    @Transaction
//    fun refresh(genresList: List<GenresEntity>){
//        deleteAll()
//        insertAll(genresList)
//    }
}
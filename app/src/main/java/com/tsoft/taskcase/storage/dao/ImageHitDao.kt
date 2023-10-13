package com.tsoft.taskcase.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tsoft.taskcase.model.ImageHit

@Dao
interface ImageHitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(imageHit: ImageHit)

    @Query("SELECT * FROM favoriteDb")
    suspend fun getFavoriteList(): List<ImageHit>

    @Query("SELECT id FROM favoriteDb")
    suspend fun getFavoriteIds(): List<Int>

    @Query("DELETE FROM favoriteDb WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)
}
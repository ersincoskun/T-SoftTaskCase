package com.tsoft.taskcase.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tsoft.taskcase.storage.dao.ImageHitDao
import com.tsoft.taskcase.model.ImageHit

@Database(entities = [ImageHit::class], version = 1)
abstract class ImageHitDb : RoomDatabase() {
    abstract fun imageHitDao(): ImageHitDao
}
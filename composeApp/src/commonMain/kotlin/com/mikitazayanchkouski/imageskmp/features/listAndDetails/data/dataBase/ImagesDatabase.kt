package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.dao.ImagesDao
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.entities.BookmarkedImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.entities.ImageEntity

@Database(
    entities = [
        ImageEntity::class,
        BookmarkedImageEntity::class
    ],
    version = 1
)
abstract class ImagesDatabase: RoomDatabase() {
    abstract val imagesDao: ImagesDao

    companion object {
        const val DATABASE_NAME = "inspirationalImagesKMP.db"
    }
}
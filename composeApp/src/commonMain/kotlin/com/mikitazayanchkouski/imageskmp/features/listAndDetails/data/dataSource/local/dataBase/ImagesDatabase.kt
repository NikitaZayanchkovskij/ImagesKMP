package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.dao.ImagesDao
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.BookmarkedImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity

@Database(
    entities = [
        ImageEntity::class,
        BookmarkedImageEntity::class
    ],
    version = 1
)
@ConstructedBy(ImagesDatabaseConstructor::class)
abstract class ImagesDatabase: RoomDatabase() {
    abstract val imagesDao: ImagesDao

    companion object {
        const val DATABASE_NAME = "inspirationalImagesKMP.db"
    }
}
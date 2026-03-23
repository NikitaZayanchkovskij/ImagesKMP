package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {

    actual fun createDatabase(): RoomDatabase.Builder<ImagesDatabase> {
        val dbFile = context.applicationContext.getDatabasePath(ImagesDatabase.DATABASE_NAME)

        return Room.databaseBuilder(
            context = context.applicationContext,
            name = dbFile.absolutePath
        )
    }
}
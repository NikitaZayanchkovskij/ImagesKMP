package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase

import androidx.room.RoomDatabase

expect class DatabaseFactory {

    fun createDatabase(): RoomDatabase.Builder<ImagesDatabase>
}
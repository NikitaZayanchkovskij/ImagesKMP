package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase

import androidx.room.RoomDatabase

expect class DatabaseFactory {

    fun createDatabase(): RoomDatabase.Builder<ImagesDatabase>
}
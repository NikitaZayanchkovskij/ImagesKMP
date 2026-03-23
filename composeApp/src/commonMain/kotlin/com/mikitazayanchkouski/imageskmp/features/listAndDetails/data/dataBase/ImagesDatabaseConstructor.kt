package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase

import androidx.room.RoomDatabaseConstructor

/** Need for Room to generate this constructor for us,
 * actual implementations of the constructor will be generated
 * by the Room compiler plugin.
 * So we don't need to implement it ourselves explicitly.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ImagesDatabaseConstructor: RoomDatabaseConstructor<ImagesDatabase> {
    override fun initialize(): ImagesDatabase
}